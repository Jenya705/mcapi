package com.github.jenya705.mcapi.test;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.module.mapper.MapperImpl;
import com.github.jenya705.mcapi.test.mock.MockServerApplication;
import com.github.jenya705.mcapi.test.mock.web.MockWebServer;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WebRoutesTestUtils {

    @SneakyThrows
    public ServerApplication create() {
        ServerApplication application = new MockServerApplication();
        application.addClass(MockWebServer.class);
        application.addClass(MapperImpl.class);
        return application;
    }

}
