package com.github.earthofmarble.utility.mapper.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by earthofmarble on Sep, 2019
 */

public class WrongReferencedTypeException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(WrongReferencedTypeException.class);

    public WrongReferencedTypeException(String message) {
        super(message);
        logger.info(message);
    }
}
