package com.github.jenya705.mcapi.server.event.database;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * @author Jenya705
 */
@Data
@RequiredArgsConstructor
public class DatabaseUpdateEvent {

    public enum Result implements Translatable {
        DELETE, CREATE, UPDATE, UPSERT;

        @Override
        public @NotNull String translationKey() {
            return "database.update.result." + name().toLowerCase(Locale.ROOT);
        }
    }

    private final Object entity;

    private final Result result;

    private boolean cancelled = false;

}
