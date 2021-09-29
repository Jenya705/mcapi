package com.github.jenya705.mcapi.module.selector;

import com.github.jenya705.mcapi.entity.AbstractBot;
import com.github.jenya705.mcapi.entity.BotLinkEntity;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@UtilityClass
public class SelectorCreatorUtils {

    private final Random random = new Random();

    public <T> Collection<T> botLinked(AbstractBot bot, Function<BotLinkEntity, T> function) {
        return Optional
                .ofNullable(bot)
                .map(AbstractBot::getLinks)
                .map(links -> links
                        .stream()
                        .map(function)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
                )
                .orElseGet(Collections::emptyList);
    }

    public <T> Collection<T> randomSingleton(List<? extends T> list) {
        return Collections.singletonList(list.get(random.nextInt(list.size())));
    }

    @SuppressWarnings("unchecked")
    public <T> Collection<T> randomSingleton(Collection<? extends T> collection) {
        if (collection instanceof List) {
            return randomSingleton((List<T>) collection);
        }
        return randomSingleton(new ArrayList<>(collection));
    }

}
