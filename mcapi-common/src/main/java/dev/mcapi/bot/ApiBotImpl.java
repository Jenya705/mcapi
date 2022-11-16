package dev.mcapi.bot;

import com.google.inject.Inject;
import dev.mcapi.database.ApplicationDatabase;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ApiBotImpl implements ApiBot {

    @Inject
    private final ApplicationDatabase database;

    private final String token;
    private final String name;
    private final UUID owner;

}
