package com.fitzone.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public static void handleException(String title, String userMessage, Exception ex) {
        logger.error("[SYSTEM ERROR] {} - {}: {}", title, userMessage, ex != null ? ex.getMessage() : "N/A", ex);
    }

    public static void logInfo(String message) {
        logger.info("[SYSTEM INFO] {}", message);
    }
}
