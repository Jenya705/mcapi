package com.github.jenya705.mcapi.command.gateway.subscriptions;

import com.github.jenya705.mcapi.stringful.Argument;
import com.github.jenya705.mcapi.stringful.Index;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class SubscriptionsGatewaysArguments {

    @Index(0)
    private String token;

    private int page = 0;

    @Index(1)
    @Argument(required = false)
    public void setPage(int page) {
        this.page = Math.max(page - 1, 0);
    }
}
