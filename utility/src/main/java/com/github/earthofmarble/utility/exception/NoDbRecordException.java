package com.github.earthofmarble.utility.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by earthofmarble on Sep, 2019
 */

public class NoDbRecordException extends RuntimeException {

    private static Logger logger = LoggerFactory.getLogger(NoDbRecordException.class);

    public NoDbRecordException(String message) {
        super(message);
        logger.info(message);
    }
}
