package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ApiSender;
import com.github.jenya705.mcapi.ApiServerApplication;
import com.github.jenya705.mcapi.token.ApiServerTokenHolderEntity;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 1.0
 * @author Jenya705
 */
public abstract class ApiServerListTokenCommand extends ApiServerTokenCommand {

    @Override
    public void execute(ApiSender sender, ApiServerCommandIterator<String> args) {
        ApiPlayer player = getPlayer(sender, args, 0);
        if (player == null) return;
        List<ApiServerTokenHolderEntity> tokenHolders = ApiServerApplication
                .getApplication()
                .getTokenRepository()
                .getHoldersByUUID(player.getUniqueId());
        sendList(sender, tokenHolders);
    }

    @Override
    public List<String> possibleVariants(ApiSender sender, ApiServerCommandIterator<String> args) {
        return ApiServerApplication.getApplication().getCore().getPlayers()
                .stream()
                .map(ApiPlayer::getName)
                .collect(Collectors.toList());
    }

    public abstract void sendList(ApiSender sender, List<ApiServerTokenHolderEntity> tokenHolders);

}
