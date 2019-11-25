package com.github.earthofmarble.dal.impl;

import com.github.earthofmarble.dal.api.IGenericDao;
import com.github.earthofmarble.model.filter.IFilter;
import com.github.earthofmarble.utility.defaultgraph.DefaultGraph;
import com.github.earthofmarble.utility.defaultgraph.DefaultGraphs;
import com.github.earthofmarble.utility.defaultgraph.Function;
import com.github.earthofmarble.utility.exception.DefaultGraphException;
import com.github.earthofmarble.utility.exception.NoIdAnnotationException;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */
@SuppressWarnings("unchecked")
public abstract class AbstractDao<T, PK extends Serializable> implements IGenericDao<T, PK> {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    protected EntityManager entityManager;

    protected Class<T> getEntityType() {
        return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String getIdFieldName(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            if (field.getAnnotation(Id.class)!=null){
                return field.getName();
            }
        }
        throw new NoIdAnnotationException("There is no field with an @Id annotation specified in class ["+clazz.getSimpleName()+"]");
    }

    private DefaultGraph getDesiredGraph(Class clazz, Function function) {
        DefaultGraphs defaultGraphs = getDefGraphsAnnotation(clazz);

        if (defaultGraphs!=null && !function.equals(Function.NONE)){

            if (defaultGraphs.value().length==0){
                throw new DefaultGraphException("The [@DefaultGraphs] annotation's value is empty! " +
                                                "There should be at least one [@DefaultGraph] nested parameter");
            }

            for (DefaultGraph graph: defaultGraphs.value()){
                if (graph.function().equals(function)){
                    return graph;
                }
            }
        }

        DefaultGraph defaultGraph = getDefGraphAnnotation(clazz);
        if (defaultGraph!=null){
            return defaultGraph;
        }

        return null;
    }

    protected EntityGraph createEntityGraph(Class clazz, Function function){
        DefaultGraph defaultGraph = getDesiredGraph(clazz, function);
        if (defaultGraph!=null) {
            return entityManager.getEntityGraph(defaultGraph.name());
        }
        return null;
    }

    protected String getFetchType(Class clazz, Function function){
        DefaultGraph defaultGraph = getDesiredGraph(clazz, function);
        if (defaultGraph!=null) {
            return defaultGraph.fetchType();
        }
        return null;
    }

    protected DefaultGraph getDefGraphAnnotation(Class clazz){
        return (DefaultGraph) clazz.getAnnotation(DefaultGraph.class);
    }

    protected DefaultGraphs getDefGraphsAnnotation(Class clazz){
        return (DefaultGraphs) clazz.getAnnotation(DefaultGraphs.class);
    }

    protected List<Order> fillOrderList(CriteriaBuilder criteriaBuilder, Root root) {
        return new ArrayList<>();
    }

    /**
     * Used in situations like {@link #readAll(IFilter filter) readAll} method,
     * if readAll called on class, which doesn't override this method, it returns empty list.
     */
    protected List<Predicate> fillPredicates(CriteriaBuilder criteriaBuilder, From rootOrJoin, IFilter filter) {
        return new ArrayList<>();
    }

    /**
     * Method, created to prevent code duplicating
     * @param clazz entity to work with
     * @param predicates list of predicates
     * @param orderList list of orders
     * @param filter - filter, can be null. if null first result if set to 0 and page size is set to 5
     */
    protected List<T> buildSelectQuery(Class clazz, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder, Root root,
                                       Function function, List<Predicate> predicates, List<Order> orderList, IFilter filter){
        Integer firstResult = 0;
        Integer pageSize = 5;
        if (filter!=null){
            firstResult = filter.getFirstElement();
            pageSize = filter.getPageSize();
        }
        EntityGraph entityGraph = createEntityGraph(clazz, function);
        criteriaQuery.select(root)
                     .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                     .orderBy(orderList);
        return entityManager.createQuery(criteriaQuery)
                            .setHint("javax.persistence."+getFetchType(clazz, function), entityGraph)
                            .setFirstResult(firstResult)
                            .setMaxResults(pageSize)
                            .getResultList();
    }

    public List<T> readByPk(PK primaryKey){
        Class clazz = getEntityType();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get(getIdFieldName(clazz)), primaryKey));

        return buildSelectQuery(clazz, criteriaQuery, criteriaBuilder, root, Function.READ_SINGLE,
                                predicates, fillOrderList(criteriaBuilder, root), null);
    }

    /**
     * @param filter should contain at least [firstElement] and [pageSize] fields
     * @return returns list of filtered database records
     */
    public List<T> readAll(IFilter filter){
        Class clazz = getEntityType();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);

        return buildSelectQuery(clazz, criteriaQuery, criteriaBuilder, root, Function.READ_BATCH,
                                fillPredicates(criteriaBuilder, root, filter), fillOrderList(criteriaBuilder, root), filter);
    }

    public void create(T model){
        entityManager.persist(model);
    }

    public void merge(T model){
        entityManager.merge(model);
    }

    public void delete(T model){
        entityManager.remove(model);
    }

}
