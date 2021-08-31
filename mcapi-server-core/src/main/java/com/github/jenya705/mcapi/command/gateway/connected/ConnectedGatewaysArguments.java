package com.github.jenya705.mcapi.command.gateway.connected;

import com.github.jenya705.mcapi.stringful.Argument;
import com.github.jenya705.mcapi.stringful.Index;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class ConnectedGatewaysArguments {

    private int page = 0;

    @Index(0)
    @Argument(required = false)
    public void setPage(int page) {
        this.page = Math.max(page - 1, 0);
    }

}
