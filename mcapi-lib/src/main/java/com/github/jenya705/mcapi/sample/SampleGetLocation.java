package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.Location;
import com.github.jenya705.mcapi.PlayerID;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.reactor.HttpRestClient;

/**
 * @author Jenya705
 */
class SampleGetLocation {

    public static void main(String[] args) {
        RestClient restClient = new HttpRestClient("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        Location location = restClient
                .getPlayerLocation(PlayerID.of("Jenya705"))
                .block();
        if (location == null) {
            System.out.println("Location is null!");
            return;
        }
        System.out.printf(
                "x: %s, y: %s, z: %s, world: %s",
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getWorld()
        );
    }

}
