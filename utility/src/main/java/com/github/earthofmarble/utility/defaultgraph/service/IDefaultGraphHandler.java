package com.github.earthofmarble.utility.defaultgraph.service;

import com.github.earthofmarble.utility.defaultgraph.annotation.DefaultGraph;
import com.github.earthofmarble.utility.defaultgraph.enumeration.Function;

/**
 * Created by earthofmarble on Nov, 2019
 */

public interface IDefaultGraphHandler {

    /**
     * Annotation DefaultGraph or DefaultGraphs should be present in given class.
     * This method takes annotation parameters and relying on this parameters returns graph, required to complete the operation
     * @param clazz entity to work with
     * @param function operation
     * @return @DefaultGraph annotations, which contains function, graph name and fetch type
     */
    DefaultGraph getDesiredGraph(Class clazz, Function function);

}
