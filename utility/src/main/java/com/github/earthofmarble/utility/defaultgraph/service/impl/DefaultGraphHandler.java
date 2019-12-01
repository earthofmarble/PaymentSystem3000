package com.github.earthofmarble.utility.defaultgraph.service.impl;

import com.github.earthofmarble.utility.defaultgraph.annotation.DefaultGraph;
import com.github.earthofmarble.utility.defaultgraph.annotation.DefaultGraphs;
import com.github.earthofmarble.utility.defaultgraph.enumeration.Function;
import com.github.earthofmarble.utility.defaultgraph.service.IDefaultGraphHandler;
import com.github.earthofmarble.utility.exception.DefaultGraphException;

/**
 * Created by earthofmarble on Nov, 2019
 */

public class DefaultGraphHandler implements IDefaultGraphHandler {

    private DefaultGraph getDefGraphAnnotation(Class clazz){
        return (DefaultGraph) clazz.getAnnotation(DefaultGraph.class);
    }

    private DefaultGraphs getDefGraphsAnnotation(Class clazz){
        return (DefaultGraphs) clazz.getAnnotation(DefaultGraphs.class);
    }

    private void validateLength(DefaultGraphs defaultGraphs){
        if (defaultGraphs.value().length==0){
            throw new DefaultGraphException("The [@DefaultGraphs] enumeration's value is empty! " +
                                            "There should be at least one [@IDefaultGraphHandler] nested parameter");
        }
    }

    public DefaultGraph getDesiredGraph(Class clazz, Function function) {
        DefaultGraphs defaultGraphs = getDefGraphsAnnotation(clazz);
        if (defaultGraphs!=null && !function.equals(Function.NONE)){
            validateLength(defaultGraphs);
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

}
