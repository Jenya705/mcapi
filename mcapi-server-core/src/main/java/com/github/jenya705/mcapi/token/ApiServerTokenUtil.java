package com.github.jenya705.mcapi.token;

import com.github.jenya705.mcapi.ApiPlayer;
import com.github.jenya705.mcapi.ApiServerApplication;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

/**
 * @since 1.0
 * @author Jenya705
 */
@UtilityClass
public class ApiServerTokenUtil {

    private static final Function<ApiPlayer, String> defaultTokenGenerator = new ApiServerTokenGenerator();

    public static String generateToken(ApiPlayer player) {
        return defaultTokenGenerator.apply(player);
    }

    public static String generateAndRegisterToken(ApiPlayer player, String name) {
        String token = generateToken(player);
        ApiServerApplication application = ApiServerApplication.getApplication();
        application.getTokenRepository().save(new ApiServerTokenHolderEntity(
                player.getUniqueId(), token, name
        ));
        return token;
    }

}
