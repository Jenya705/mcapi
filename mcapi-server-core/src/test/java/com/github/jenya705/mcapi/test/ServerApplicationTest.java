package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.*;
import com.github.jenya705.mcapi.module.web.tunnel.EventTunnel;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

/**
 * @author Jenya705
 */
public class ServerApplicationTest {

    @Getter
    public static class Counter {
        private int current;

        public int next() {
            return current++;
        }
    }

    public static class Initializing1 {
        @Bean
        private Counter counter;

        @OnInitializing
        public void initialize() {
            if (counter.next() != 0) throw new IllegalStateException("Bad order to initialize objects");
        }
    }

    public static class Initializing2 {
        @Bean
        private Counter counter;

        @OnInitializing(priority = 4)
        public void initialize() {
            if (counter.next() != 1) throw new IllegalStateException("Bad order to initialize objects");
        }
    }

    public static class Starting1 {
        @Bean
        private Counter counter;

        @OnStartup
        public void start() {
            if (counter.next() != 2) throw new IllegalStateException("Bad order to initialize objects");
        }
    }

    public static class Stopping1 {
        @Bean
        private Counter counter;

        @OnDisable
        public void stop() {
            if (counter.next() != 3) throw new IllegalStateException("Bad order to initialize objects");
        }
    }

    public static class Failing {
        @OnStartup
        public void start() {
            throw new IllegalStateException("Just exception");
        }
    }

    @Test
    public void initializingTest() {
        ServerApplication application =
                runWithClasses(Counter.class, Initializing1.class, Initializing2.class, Starting1.class, Stopping1.class);
        Assertions.assertTrue(application.isEnabled());
    }

    @Test
    public void failedInitializingTest() {
        ServerApplication application = runWithClasses(Failing.class, Initializing1.class);
        Assertions.assertFalse(application.isEnabled());
    }

    private ServerApplication runWithClasses(Class<?>... classes) {
        ServerApplication application = new ServerApplication();
        application.addBean(Mockito.mock(ServerCore.class));
        application.addBean(Mockito.mock(EventTunnel.class));
        application.addBean(Mockito.mock(ServerLocalEventHandler.class));
        application.setPlatform(ServerPlatform.OTHER);
        application.getClasses().clear();
        application.getClasses().addAll(Arrays.asList(classes));
        application.start();
        application.stop();
        return application;
    }
}
