package com.github.jenya705.mcapi.test.mock;

import com.github.jenya705.mcapi.ServerApplication;
import lombok.experimental.Delegate;

/**
 * @author Jenya705
 */
public class TestApplication {

    @Delegate
    private final ServerApplication application;

    public static TestApplication get() {
        return singleton;
    }

    private static final TestApplication singleton = new TestApplication();

    private TestApplication() {
        application = new ServerApplication();
    }

}
