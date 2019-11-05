package com.github.earthofmarble.utility.defaultgraph;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultGraph {
    Function function();

    String name();

    String fetchType() default "fetchgraph";
}
