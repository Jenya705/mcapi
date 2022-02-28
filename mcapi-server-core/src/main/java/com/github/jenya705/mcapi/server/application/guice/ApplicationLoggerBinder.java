package com.github.jenya705.mcapi.server.application.guice;

import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jenya705
 */
public class ApplicationLoggerBinder extends AbstractModule {

    private static final Logger log = LoggerFactory.getLogger("mcapi");

    @Override
    protected void configure() {
        bind(Logger.class).toInstance(log);
    }
}
