package dev.mcapi.bot;

import java.util.UUID;

public interface ApiBot {

    String getToken();

    String getName();

    UUID getOwner();

}
