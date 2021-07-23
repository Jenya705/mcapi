package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ApiSender;
import com.github.jenya705.mcapi.ApiServerApplication;
import com.github.jenya705.mcapi.token.ApiServerTokenUtil;

import java.util.Iterator;
import java.util.UUID;

/**
 * @since 1.0
 * @author Jenya705
 */
public abstract class ApiServerCreateTokenCommand implements ApiServerCommandExecutor {

    @Override
    public void execute(ApiSender sender, Iterator<String> args) {
        ApiPlayer player;
        if (sender instanceof ApiPlayer) {
            player = (ApiPlayer) sender;
        }
        else {
            if (!args.hasNext()) {
                sendMessagePlayerNameIsNotGiven(sender);
                return;
            }
            player = ApiServerApplication.getApplication().getCore().getPlayer(args.next());
            if (player == null) {
                sendMessagePlayerIsNotExist(sender);
                return;
            }
        }
        if (!args.hasNext()) {
            sendMessageTokenNameIsNotGiven(sender);
            return;
        }
        String tokenName = args.next();
        String generatedToken = ApiServerTokenUtil.generateAndRegisterToken(player, tokenName);
        sendMessageSuccess(sender, generatedToken, tokenName);
    }

    public abstract void sendMessagePlayerNameIsNotGiven(ApiSender sender);

    public abstract void sendMessagePlayerIsNotExist(ApiSender sender);

    public abstract void sendMessageTokenNameIsNotGiven(ApiSender sender);

    public abstract void sendMessageSuccess(ApiSender sender, String token, String name);

}
