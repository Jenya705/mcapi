package com.github.jenya705.mcapi.server.application;

import com.github.jenya705.mcapi.event.*;
import com.github.jenya705.mcapi.rest.entity.RestCapturedEntityClickEvent;
import com.github.jenya705.mcapi.rest.event.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Jenya705
 */
@Singleton
public class EventBroadcaster extends AbstractApplicationModule {

    @Inject
    public EventBroadcaster(ServerApplication application) {
        super(application);
    }

    @OnStartup
    public void start() {
        eventLoop()
                .asyncHandler(
                        MessageEvent.class,
                        it -> eventTunnel()
                                .broadcast(RestMessageEvent.from(it), RestMessageEvent.type)
                )
                .asyncHandler(
                        JoinEvent.class,
                        it -> eventTunnel()
                                .broadcast(RestJoinEvent.from(it), RestJoinEvent.type)
                )
                .asyncHandler(
                        QuitEvent.class,
                        it -> eventTunnel()
                                .broadcast(RestQuitEvent.from(it), RestQuitEvent.type)
                )
                .asyncHandler(
                        PlayerBlockPlaceEvent.class,
                        it -> eventTunnel()
                                .broadcast(RestPlayerBlockPlaceEvent.from(it), RestPlayerBlockPlaceEvent.type)
                )
                .asyncHandler(
                        PlayerBlockBreakEvent.class,
                        it -> eventTunnel()
                                .broadcast(RestPlayerBlockBreakEvent.from(it), RestPlayerBlockBreakEvent.type)
                )
                .asyncHandler(
                        CapturedEntityClickEvent.class,
                        it -> eventTunnel()
                                .getClients()
                                .stream()
                                .filter(client -> client.getOwner().getEntity().getId() == it.getEntity().getOwner())
                                .filter(client -> client.isSubscribed(RestCapturedEntityClickEvent.type))
                                .forEach(client -> client.send(it))
                )
                .asyncHandler(
                        InventoryMoveEvent.class,
                        it -> eventTunnel()
                                .broadcast(RestInventoryMoveEvent.from(it), RestInventoryMoveEvent.type)
                )
        ;
    }
}
