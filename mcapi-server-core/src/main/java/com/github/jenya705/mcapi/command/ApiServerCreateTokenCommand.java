package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ApiSender;
import com.github.jenya705.mcapi.ApiServerApplication;
import com.github.jenya705.mcapi.token.ApiServerTokenUtil;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @since 1.0
 * @author Jenya705
 */
public abstract class ApiServerCreateTokenCommand extends ApiServerTokenCommand {

    @Override
    public void execute(ApiSender sender, ApiServerCommandIterator<String> args) {
        ApiPlayer player = getPlayer(sender, args, 1);
        if (player == null) return;
        if (!args.hasNext()) {
            sendMessageTokenNameIsNotGiven(sender);
            return;
        }
        String tokenName = args.next();
        String generatedToken = ApiServerTokenUtil.generateAndRegisterToken(player, tokenName);
        sendMessageSuccess(sender, generatedToken, tokenName);
    }

    @Override
    public List<String> possibleVariants(ApiSender sender, ApiServerCommandIterator<String> args) {
        return ApiServerApplication.getApplication().getCore()
                .getPlayers()
                .stream()
                .map(ApiPlayer::getName)
                .collect(Collectors.toList());
    }

    public abstract void sendMessageTokenNameIsNotGiven(ApiSender sender);

    public abstract void sendMessageSuccess(ApiSender sender, String token, String name);

}
