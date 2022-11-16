package dev.mcapi.database;

import com.google.inject.ImplementedBy;
import dev.mcapi.bot.ApiBot;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ImplementedBy(R2DBCApplicationDatabase.class)
public interface ApplicationDatabase extends AutoCloseable {

    Mono<ApiBot> findBotByName(String name);

    Mono<ApiBot> findBotByToken(String token);

    Flux<ApiBot> findBotsByOwner(UUID uuid);

}

