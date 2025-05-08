package com.erling.utilJ.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LOGGER {
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
