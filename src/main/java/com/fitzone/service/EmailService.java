package com.fitzone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public static boolean sendEmail(String recipient, String subject, String body) {
        logger.info("Sending Email to: {} | Subject: {}", recipient, subject);
        return true; // Web notification logger
    }
}
