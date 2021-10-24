package com.github.jenya705.mcapi.sample;

import com.github.jenya705.mcapi.DefaultMessage;
import com.github.jenya705.mcapi.RestClient;
import com.github.jenya705.mcapi.reactor.HttpRestClient;
import com.github.jenya705.mcapi.selector.PlayerSelector;

/**
 * @author Jenya705
 */
class SampleSendMessage {

    public static void main(String[] args) {
        RestClient restClient = new HttpRestClient("localhost", 8080, "ce727c0a74024afdbd6ed9d03225d4e60000001630142908370"); // some token
        restClient.sendMessage(PlayerSelector.all, new DefaultMessage("Hi!")).block();
    }

}
