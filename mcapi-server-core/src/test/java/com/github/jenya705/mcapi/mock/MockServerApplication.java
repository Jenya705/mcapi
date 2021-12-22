package com.github.jenya705.mcapi.mock;

import com.github.jenya705.mcapi.Bean;
import com.github.jenya705.mcapi.OnInitializing;
import com.github.jenya705.mcapi.ServerApplication;
import com.github.jenya705.mcapi.entity.EntityLocation;
import com.github.jenya705.mcapi.entity.player.EntityPlayer;
import com.github.jenya705.mcapi.mock.database.MockDatabaseModule;
import com.github.jenya705.mcapi.mock.web.MockWebServer;
import com.github.jenya705.mcapi.module.database.DatabaseModule;
import com.github.jenya705.mcapi.module.web.WebServer;
import com.github.jenya705.mcapi.player.GameMode;
import com.github.jenya705.mcapi.player.Player;
import org.mockito.Mockito;

import java.util.*;

public class MockServerApplication extends ServerApplication {

    private final List<Player> players = Arrays.asList(
            createMockPlayer(new EntityPlayer(
                    "Jenya705",
                    UUID.randomUUID(),
                    "player",
                    true,
                    null,
                    new EntityLocation(0, 10, 0, null),
                    null,
                    null,
                    GameMode.SURVIVAL,
                    null
            )),
            createMockPlayer(new EntityPlayer(
                    "DJ5613",
                    UUID.randomUUID(),
                    "player",
                    true,
                    null,
                    new EntityLocation(0, 10, 0, null),
                    null,
                    null,
                    GameMode.SURVIVAL,
                    null
            )),
            createMockPlayer(new EntityPlayer(
                    "ladola",
                    UUID.randomUUID(),
                    "player",
                    true,
                    null,
                    new EntityLocation(0, 10, 0, null),
                    null,
                    null,
                    GameMode.SURVIVAL,
                    null
            )),
            createMockPlayer(new EntityPlayer(
                    "evtushok",
                    UUID.randomUUID(),
                    "player",
                    true,
                    null,
                    new EntityLocation(0, 10, 0, null),
                    null,
                    null,
                    GameMode.SURVIVAL,
                    null
            ))
    );

    private static Player createMockPlayer(Player entity) {
        Player mock = Mockito.mock(Player.class);
        Mockito.when(mock.getName()).thenReturn(entity.getName());
        Mockito.when(mock.getLocation()).thenReturn(entity.getLocation());
        Mockito.when(mock.getType()).thenReturn(entity.getType());
        Mockito.when(mock.getUuid()).thenReturn(entity.getUuid());
        Mockito.when(mock.isOnline()).thenReturn(entity.isOnline());
        return mock;
    }

    @Bean
    private MockServerCore core;

    public MockServerApplication() {
        addClass(MockServerCore.class);
        change(DatabaseModule.class, MockDatabaseModule.class);
        change(WebServer.class, MockWebServer.class);
    }

    @OnInitializing(priority = Integer.MIN_VALUE)
    public void mockApplicationInitialize() throws Exception {
        Map<String, Object> config = new HashMap<>();
        Map<String, Object> databaseConfig = new HashMap<>();
        config.put("database", databaseConfig);
        databaseConfig.put("type", "h2");
        core.saveConfig("config", config);
    }

    public static MockServerApplication mock() {
        return new MockServerApplication();
    }

    public MockServerApplication withPlayers(int count) {
        if (count > players.size()) {
            throw new IllegalArgumentException("too many players");
        }
        MockServerCore core = getBean(MockServerCore.class);
        for (int i = 0; i < count; ++i) {
            core.addPlayer(players.get(i));
        }
        return this;
    }

    public MockServerApplication change(Class<?> from, Class<?> to) {
        for (int i = 0; i < getClasses().size(); ++i) {
            if (from.isAssignableFrom(getClasses().get(i))) {
                getClasses().set(i, to);
            }
        }
        return this;
    }

    public MockServerApplication run() {
        start();
        return this;
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

}
