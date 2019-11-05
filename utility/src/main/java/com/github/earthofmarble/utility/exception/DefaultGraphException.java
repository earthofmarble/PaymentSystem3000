package com.github.earthofmarble.utility.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by earthofmarble on Sep, 2019
 */

public class DefaultGraphException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(DefaultGraphException.class);

    public DefaultGraphException(String message) {
        super(message);
        logger.info(message);
    }
}
