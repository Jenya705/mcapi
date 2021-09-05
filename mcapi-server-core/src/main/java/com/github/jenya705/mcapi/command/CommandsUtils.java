package com.github.jenya705.mcapi.command;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@UtilityClass
public class CommandsUtils {

    public final char colorsChar = '\u00A7';

    public String placeholderMessage(String message, String... placeholders) {
        String messageWithPlaceholders = message
                .replaceAll("&", Character.toString(colorsChar));
        for (int i = 0; i < placeholders.length; i += 2) {
            messageWithPlaceholders = messageWithPlaceholders
                    .replaceAll(placeholders[i], placeholders[i + 1]);
        }
        return messageWithPlaceholders;
    }

    public <T> String listMessage(String layout, String element, String delimiter, List<T> elements, Function<T, String[]> placeholdersGet, String... layoutPlaceholders) {
        return listMessage(layout, element, delimiter, elements, placeholdersGet, elements.size(), 0, layoutPlaceholders);
    }

    public <T> String listMessage(
            String layout,
            String element,
            String delimiter,
            List<T> elements,
            Function<T, String[]> placeholdersGet,
            int size,
            int page,
            String... layoutPlaceholders
    ) {
        String[] fullLayoutPlaceholders = Arrays.copyOf(layoutPlaceholders, layoutPlaceholders.length + 2);
        fullLayoutPlaceholders[fullLayoutPlaceholders.length - 2] = "%list%";
        fullLayoutPlaceholders[fullLayoutPlaceholders.length - 1] =
                elements
                        .stream()
                        .skip((long) size * page)
                        .limit(size)
                        .map(placeholdersGet)
                        .map(it -> placeholderMessage(element, it))
                        .collect(Collectors.joining(placeholderMessage(delimiter)));
        return placeholderMessage(layout, fullLayoutPlaceholders);
    }
}
