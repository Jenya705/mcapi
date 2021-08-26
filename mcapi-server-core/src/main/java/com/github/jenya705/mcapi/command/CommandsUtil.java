package com.github.jenya705.mcapi.command;

import lombok.experimental.UtilityClass;

/**
 * @author Jenya705
 */
@UtilityClass
public class CommandsUtil {

    public static final char colorsChar = '\u00A7';

    public String placeholderMessage(String message, String... placeholders) {
        String messageWithPlaceholders = message
                .replaceAll("&", Character.toString(colorsChar));
        for (int i = 0; i < placeholders.length; i += 2) {
            messageWithPlaceholders = messageWithPlaceholders
                    .replaceAll(placeholders[i], placeholders[i+1]);
        }
        return messageWithPlaceholders;
    }

}
