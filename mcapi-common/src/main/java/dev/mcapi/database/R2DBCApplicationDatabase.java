package dev.mcapi.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.mcapi.bot.ApiBot;
import dev.mcapi.bot.ApiBotImpl;
import dev.mcapi.config.ConfigContainer;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.*;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Singleton
public class R2DBCApplicationDatabase implements ApplicationDatabase {

    private final ConnectionFactory factory;
    private final ConnectionPool pool;

    @Inject
    public R2DBCApplicationDatabase(ConfigContainer configContainer) {
        factory = ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(ConnectionFactoryOptions.DRIVER, "pool")
                        .option(ConnectionFactoryOptions.PROTOCOL, "mysql")
                        .option(ConnectionFactoryOptions.USER, configContainer.getDefaultData().get("sql.user"))
                        .option(ConnectionFactoryOptions.PASSWORD, configContainer.getDefaultData().get("sql.password"))
                        .option(ConnectionFactoryOptions.HOST, configContainer.getDefaultData().get("sql.host"))
                        .option(ConnectionFactoryOptions.PORT, configContainer.getDefaultData().get("sql.port"))
                        .option(ConnectionFactoryOptions.DATABASE, configContainer.getDefaultData().get("sql.database"))
                        .option(ConnectionFactoryOptions.SSL, configContainer.getDefaultData().get("sql.ssl"))
                        .build()
        );
        pool = new ConnectionPool(
                ConnectionPoolConfiguration.builder(factory)
                        .maxSize(configContainer.getDefaultData().get("sql.pool-size"))
                        .build()
        );
    }

    @Override
    public void close() {
        pool.close().block();
    }

    public Mono<Connection> getConnection() {
        return Mono.fromDirect(factory.create());
    }

    private Publisher<ApiBot> fromResult(Result result) {
        return result.map((row, metadata) -> ApiBotImpl
                .builder()
                .database(this)
                .name(row.get(0, String.class))
                .token(row.get(1, String.class))
                .owner(UUID.fromString(row.get(2, String.class)))
                .build()
        );
    }

    @Override
    public Mono<ApiBot> findBotByName(String name) {
        return Mono.usingWhen(
                getConnection(),
                connection -> Mono
                        .fromDirect(connection
                                .createStatement("SELECT * FROM bots WHERE name = ?")
                                .bind(0, name)
                                .execute()
                        )
                        .flatMapMany(this::fromResult)
                        .singleOrEmpty(),
                Connection::close
        );
    }

    @Override
    public Mono<ApiBot> findBotByToken(String token) {
        return Mono.usingWhen(
                getConnection(),
                connection -> Mono
                        .fromDirect(connection
                                .createStatement("SELECT * FROM bots WHERE token = ?")
                                .bind(0, token)
                                .execute()
                        )
                        .flatMapMany(this::fromResult)
                        .singleOrEmpty(),
                Connection::close
        );
    }

    @Override
    public Flux<ApiBot> findBotsByOwner(UUID uuid) {
        return Flux.usingWhen(
                getConnection(),
                connection -> Mono
                        .fromDirect(connection
                                .createStatement("SELECT * FROM bots WHERE owner = ?")
                                .bind(0, uuid.toString())
                                .execute()
                        )
                        .flatMapMany(this::fromResult),
                Connection::close
        );
    }
}
