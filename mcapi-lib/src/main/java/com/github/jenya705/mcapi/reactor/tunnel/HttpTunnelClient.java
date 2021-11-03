package com.github.jenya705.mcapi.reactor.tunnel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jenya705.mcapi.TunnelClient;
import com.github.jenya705.mcapi.app.LibraryApplication;
import com.github.jenya705.mcapi.command.CommandInteractionValue;
import com.github.jenya705.mcapi.entity.LazyCommandSender;
import com.github.jenya705.mcapi.entity.LazyOfflinePlayer;
import com.github.jenya705.mcapi.entity.LazyPlayer;
import com.github.jenya705.mcapi.entity.command.EntityCommandInteractionValue;
import com.github.jenya705.mcapi.entity.event.*;
import com.github.jenya705.mcapi.event.*;
import com.github.jenya705.mcapi.reactor.rest.HttpRestClient;
import com.github.jenya705.mcapi.reactor.rest.ReactorBlockingThread;
import com.github.jenya705.mcapi.rest.RestEventTunnelAuthorizationRequest;
import com.github.jenya705.mcapi.rest.RestSubscribeRequest;
import com.github.jenya705.mcapi.rest.command.RestCommandInteractionEvent;
import com.github.jenya705.mcapi.rest.event.*;
import com.github.jenya705.mcapi.utils.ZipUtils;
import lombok.AccessLevel;
import lombok.Setter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.netty.http.client.HttpClient;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Jenya705
 */
@Setter(AccessLevel.PROTECTED)
public class HttpTunnelClient implements TunnelClient {

    private final LibraryApplication<?, ?> application;

    private final Map<Class<?>, Set<Consumer<Object>>> eventHandlers = new HashMap<>();
    private final Map<String, Class<?>> eventTypes = new HashMap<>();

    private HttpClient client;
    private FluxSink<String> sendFlux;
    private Runnable disableRunnable;
    private boolean disabled;
    private ReactorBlockingThread blockingThread;

    private final List<String> subscriptions = new ArrayList<>();

    public HttpTunnelClient(LibraryApplication<?, ?> application) {
        this.application = application;
        application.onStart(() -> {
            if (disabled) return;
            blockingThread = ((HttpRestClient) application.rest())
                    .getBlockingThread();
            client = HttpClient
                    .create()
                    .host(application.getIp())
                    .port(application.getPort());
            connectWebSocket();
        });
        addEventType(CommandInteractionEvent.class, RestCommandInteractionEvent.type);
        addEventType(JoinEvent.class, RestJoinEvent.type);
        addEventType(LinkEvent.class, RestLinkEvent.type);
        addEventType(MessageEvent.class, RestMessageEvent.type);
        addEventType(QuitEvent.class, RestQuitEvent.type);
        addEventType(SubscribeEvent.class, RestSubscribeEvent.type);
        addEventType(UnlinkEvent.class, RestUnlinkEvent.type);
        application.addDeserializer(
                RestCommandInteractionEvent.class,
                CommandInteractionEvent.class,
                event -> new EntityCommandInteractionEvent(
                        event.getPath(),
                        LazyCommandSender.of(
                                application.rest(),
                                event.getCommandSender()
                        ),
                        Arrays
                                .stream(event.getValues())
                                .map(value -> new EntityCommandInteractionValue(
                                        value.getName(),
                                        value.getValue()
                                ))
                                .toArray(CommandInteractionValue[]::new)
                )
        );
        application.addDeserializer(
                RestJoinEvent.class,
                JoinEvent.class,
                event -> new EntityJoinEvent(
                        LazyPlayer.of(
                                application.rest(),
                                event.getPlayer()
                        )
                )
        );
        application.addDeserializer(
                RestLinkEvent.class,
                LinkEvent.class,
                event -> new EntityLinkEvent(
                        event.isFailed(),
                        LazyPlayer.of(
                                application.rest(),
                                event.getPlayer()
                        ),
                        event.getDeclinePermissions()
                )
        );
        application.addDeserializer(
                RestMessageEvent.class,
                MessageEvent.class,
                event -> new EntityMessageEvent(
                        event.getMessage(),
                        LazyPlayer.of(
                                application.rest(),
                                event.getAuthor()
                        )
                )
        );
        application.addDeserializer(
                RestQuitEvent.class,
                QuitEvent.class,
                event -> new EntityQuitEvent(
                        LazyOfflinePlayer.of(
                                application.rest(),
                                event.getPlayer()
                        )
                )
        );
        application.addDeserializer(
                RestSubscribeEvent.class,
                SubscribeEvent.class,
                event -> new EntitySubscribeEvent(
                        event.getFailed()
                )
        );
        application.addDeserializer(
                RestUnlinkEvent.class,
                UnlinkEvent.class,
                event -> new EntityUnlinkEvent(
                        LazyPlayer.of(
                                application.rest(),
                                event.getPlayer()
                        )
                )
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void on(Class<? extends T> clazz, Consumer<T> handler) {
        if (!eventHandlers.containsKey(clazz)) {
            eventHandlers.put(clazz, new HashSet<>());
            for (Map.Entry<String, Class<?>> entry : eventTypes.entrySet()) {
                if (entry.getValue() == clazz) sendSubscriptions(entry.getKey());
            }
        }
        eventHandlers.get(clazz).add(obj -> handler.accept((T) obj));
    }

    @Override
    public void addEventType(Class<?> clazz, String type) {
        eventTypes.put(type, clazz);
    }

    @Override
    public void disable() {
        if (disableRunnable != null) {
            disableRunnable.run();
        }
        disabled = true;
    }

    private void connectWebSocket() {
        client
                .websocket()
                .uri("/tunnel")
                .handle((inbound, outbound) -> {
                    blockingThread.addTask(
                            inbound
                                    .receive()
                                    .asByteArray()
                                    .map(ZipUtils::decompressSneaky)
                                    .map(String::new)
                                    .map(this::getEvent)
                    ).subscribe(event -> {
                        Set<Consumer<Object>> handlers = getEventHandlers(event.getClass());
                        if (handlers == null) return;
                        handlers
                                .forEach(handler -> handler.accept(event));
                    });
                    disableRunnable = outbound::sendClose;
                    return outbound.sendByteArray(
                            Flux
                                    .<String>create(sink -> {
                                        sendFlux = sink;
                                        sendObject(new RestEventTunnelAuthorizationRequest(
                                                application.getToken()
                                        ));
                                        sendObject(new RestSubscribeRequest(
                                                subscriptions.toArray(String[]::new)
                                        ));
                                    })
                                    .map(String::getBytes)
                                    .map(ZipUtils::compressSneaky)
                    );
                })
                .subscribe();
    }

    private Object getEvent(String json) {
        System.out.println("Json: " + json);
        JsonNode node = application.fromJson(json, JsonNode.class);
        String eventType = node.get("type").asText();
        Class<?> eventClass = eventTypes.get(eventType);
        if (eventClass == null) {
            throw new IllegalArgumentException("Given type is not registered in client");
        }
        ((ObjectNode) node).remove("type");
        String newJson = application.asJson(node);
        return application.fromJson(newJson, eventClass);
    }

    private Set<Consumer<Object>> getEventHandlers(Class<?> eventClass) {
        for (Map.Entry<Class<?>, Set<Consumer<Object>>> entry : eventHandlers.entrySet()) {
            if (entry.getKey().isAssignableFrom(eventClass)) return entry.getValue();
        }
        return null;
    }

    public void sendObject(Object obj) {
        sendFlux.next(application.asJson(obj));
    }

    private void sendSubscriptions(String... subscriptions) {
        this.subscriptions.addAll(Arrays.asList(subscriptions));
        if (sendFlux != null) {
            sendObject(new RestSubscribeRequest(subscriptions));
        }
    }
}
