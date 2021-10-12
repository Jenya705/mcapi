package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.module.web.RouteHandler;
import com.github.jenya705.mcapi.test.mock.MockServerApplication;
import lombok.SneakyThrows;

import java.util.List;
import java.util.stream.Collectors;

public class WebRoutesTest {

    @SneakyThrows
    private ServerApplication create() {
        MockServerApplication applicationMock = new MockServerApplication(false);
        List<Class<?>> newClasses = applicationMock
                .getClasses()
                .stream()
                .filter(it -> it.isAssignableFrom(RouteHandler.class))
                .collect(Collectors.toList());
        applicationMock.getClasses().clear();
        newClasses.forEach(applicationMock::addClass);
        return applicationMock;
    }

}
