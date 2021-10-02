package com.github.jenya705.mcapi.log;

import org.slf4j.Logger;

/**
 * @author Jenya705
 */
public class TimerTaskImpl implements TimerTask {

    private final Logger logger;

    private long start;
    private String message;

    public TimerTaskImpl(Logger logger, String message) {
        this.logger = logger;
        start(message);
    }

    @Override
    public void start(String message) {
        this.message = message;
        start = System.currentTimeMillis();
        logger.info(message);
    }

    @Override
    public void complete() {
        logger.info(String.format("Done! (%s) %dms", message, System.currentTimeMillis() - start));
    }
}
