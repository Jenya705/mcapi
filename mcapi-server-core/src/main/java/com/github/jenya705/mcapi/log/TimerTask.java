package com.github.jenya705.mcapi.log;

import org.slf4j.Logger;

/**
 * @author Jenya705
 */
public interface TimerTask {

    static TimerTask start(Logger logger, String message) {
        return new TimerTaskImpl(logger, message);
    }

    void complete();
}
