package com.github.jenya705.mcapi.command;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ApiSender;
import com.github.jenya705.mcapi.ApiServerApplication;

import java.util.Iterator;

/**
 * @since 1.0
 * @author Jenya705
 */
public abstract class ApiServerTokenCommand implements ApiServerCommandExecutor {

    protected ApiPlayer getPlayer(ApiSender sender, ApiServerCommandIterator<String> args, int nextArgsCount) {
        ApiPlayer player = null;
        if (args.hasNext(nextArgsCount + 1)) {
            player = ApiServerApplication.getApplication().getCore().getPlayer(args.next());
            if (player == null) {
                sendMessagePlayerIsNotExist(sender);
            }
        }
        else if (sender instanceof ApiPlayer){
            player = (ApiPlayer) sender;
        }
        else {
            sendMessagePlayerNameIsNotGiven(sender);
        }
        return player;
    }

    public abstract void sendMessagePlayerNameIsNotGiven(ApiSender sender);

    public abstract void sendMessagePlayerIsNotExist(ApiSender sender);

}
