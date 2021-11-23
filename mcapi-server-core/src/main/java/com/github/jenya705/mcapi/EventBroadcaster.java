package com.github.jenya705.mcapi;

import com.github.jenya705.mcapi.event.*;
import com.github.jenya705.mcapi.rest.event.*;

/**
 * @author Jenya705
 */
public class EventBroadcaster extends AbstractApplicationModule {

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
        ;
    }

}
