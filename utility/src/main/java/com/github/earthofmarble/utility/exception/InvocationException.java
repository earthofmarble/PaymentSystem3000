package com.github.earthofmarble.utility.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by earthofmarble on Sep, 2019
 */

public class InvocationException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(InvocationException.class);

    public InvocationException(String message) {
        super(message);
        logger.info(message);
    }
}
