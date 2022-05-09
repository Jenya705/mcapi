package com.github.jenya705.mcapi.server.module.selector;

import com.github.jenya705.mcapi.server.entity.AbstractBot;
import com.github.jenya705.mcapi.server.entity.BotLinkEntity;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@UtilityClass
public class SelectorCreatorUtils {

    private final Random random = new Random();

    public <T> Flux<T> botLinked(AbstractBot bot, Function<BotLinkEntity, Mono<T>> function) {
        return Optional
                .ofNullable(bot)
                .map(AbstractBot::getLinks)
                .map(links -> Flux.merge(
                        links
                                .stream()
                                .map(function)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())
                ))
                .orElseGet(Flux::empty);
    }

    public <T> T randomSingleton(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    @SuppressWarnings("unchecked")
    public <T> T randomSingleton(Collection<T> collection) {
        if (collection instanceof List) {
            return randomSingleton((List<T>) collection);
        }
        return randomSingleton(new ArrayList<>(collection));
    }

}
