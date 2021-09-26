package com.github.jenya705.mcapi.log;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;

/**
 * @author Jenya705
 */
public class TimerTaskImpl implements TimerTask {

    private final long start;
    private final Logger logger;
    private final String message;

    public TimerTaskImpl(Logger logger, String message) {
        this.message = message;
        this.logger = logger;
        start = System.currentTimeMillis();
        logger.info(message);
    }

    @Override
    public void complete() {
        logger.info(String.format("Done! (%s) %d ms", message, System.currentTimeMillis() - start));
    }
}
