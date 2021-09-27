package com.github.jenya705.mcapi.entity.api;

import com.github.jenya705.mcapi.ApiCommandSender;
import com.github.jenya705.mcapi.entity.RestCommandSender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCommandSender implements ApiCommandSender {

    private String name;
    private String type;

    @Override
    public void sendMessage(String message) {
        EntityUtils.throwEntityContextException();
    }

    @Override
    public boolean hasPermission(String permission) {
        EntityUtils.throwEntityContextException();
        return true;
    }

    public RestCommandSender rest() {
        return RestCommandSender.from(this);
    }
}
