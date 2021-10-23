package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.Player;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.reactor.HttpRestClient;

/**
 * @author Jenya705
 */
class SampleGetPlayers {

    public static void main(String[] args) {
        RestClient restClient = new HttpRestClient("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        restClient
                .getOnlinePlayers()
                .map(Player::getName)
                .collectList()
                .block()
                .forEach(System.out::println);
    }

}
