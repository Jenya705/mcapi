package com.github.jenya705.mcapi.test.mock;

import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.ServerCore;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jenya705
 */
public class ServerApplicationMock extends ServerApplication {

    public ServerApplicationMock() throws IOException {
        getClasses().clear();
        ServerCore core = Mockito.mock(ServerCore.class);
        addBean(core);
        Mockito.when(core.loadConfig(Mockito.any())).thenReturn(new HashMap<>());
    }

    @Override
    public <T> T getBean(Class<? extends T> clazz) {
        T bean = super.getBean(clazz);
        if (bean == null) {
            bean = Mockito.mock(clazz);
        }
        return bean;
    }
}
