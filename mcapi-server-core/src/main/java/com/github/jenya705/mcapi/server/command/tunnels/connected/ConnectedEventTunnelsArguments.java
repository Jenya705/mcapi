package com.github.jenya705.mcapi.server.command.tunnels.connected;

import com.github.jenya705.mcapi.server.stringful.Argument;
import com.github.jenya705.mcapi.server.stringful.Index;
import lombok.Data;

/**
 * @author Jenya705
 */
@Data
public class ConnectedEventTunnelsArguments {

    private int page = 0;

    @Index(0)
    @Argument(required = false)
    public void setPage(int page) {
        this.page = Math.max(page - 1, 0);
    }
}
