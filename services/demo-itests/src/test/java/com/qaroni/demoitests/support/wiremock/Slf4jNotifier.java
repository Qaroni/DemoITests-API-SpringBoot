package com.qaroni.demoitests.support.wiremock;

import com.github.tomakehurst.wiremock.common.Notifier;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class Slf4jNotifier implements Notifier {

    private Logger logger = LoggerFactory.getLogger(Slf4jNotifier.class);

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
