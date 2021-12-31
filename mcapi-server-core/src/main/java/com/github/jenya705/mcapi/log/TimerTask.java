package com.github.jenya705.mcapi.log;

import org.slf4j.Logger;

/**
 * @author Jenya705
 */
public interface TimerTask {

    static TimerTask start(Logger logger, String message) {
        return new TimerTaskImpl(logger, message);
    }

    static TimerTask start(Logger logger, String format, Object... args) {
        return start(logger, String.format(format, args));
    }

    void start(String message);

    void complete();
}
