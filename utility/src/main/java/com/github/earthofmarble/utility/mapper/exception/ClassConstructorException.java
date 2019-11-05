package com.github.earthofmarble.utility.mapper.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by earthofmarble on Sep, 2019
 */

public class ClassConstructorException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(ClassConstructorException.class);

    public ClassConstructorException(String message) {
        super(message);
        logger.info(message);
    }
}
