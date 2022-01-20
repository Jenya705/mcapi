package com.github.jenya705.mcapi.server.util;

/**
 * @author Jenya705
 */
@FunctionalInterface
public interface ExceptionableRunnable {

    void run() throws Exception;

}
