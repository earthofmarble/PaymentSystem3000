package com.github.earthofmarble.utility.mapper.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by earthofmarble on Sep, 2019
 */

public class IsNotConvertibleException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(IsNotConvertibleException.class);

    public IsNotConvertibleException(String message) {
        super(message);
        logger.info(message);
    }
}
