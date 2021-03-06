package com.github.jenya705.mcapi.rest.player;

import com.github.jenya705.mcapi.jackson.DefaultBoolean;
import com.github.jenya705.mcapi.jackson.DefaultFloat;
import com.github.jenya705.mcapi.jackson.DefaultNull;
import com.github.jenya705.mcapi.jackson.DefaultString;
import com.github.jenya705.mcapi.player.Player;
import com.github.jenya705.mcapi.rest.RestLocation;
import com.github.jenya705.mcapi.rest.inventory.RestInventory;
import com.github.jenya705.mcapi.rest.potion.RestPotionEffect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPlayer {

    private String name;
    private UUID uuid;
    private String type;
    @DefaultNull
    private RestLocation location;
    @DefaultString("SURVIVAL")
    private String gameMode;
    private RestPlayerAbilities abilities;
    @DefaultNull
    private Component customName;
    @DefaultFloat(20)
    private float health;
    @DefaultBoolean(false)
    private boolean ai;
    private RestInventory inventory;
    @DefaultNull
    private List<RestPotionEffect> effects;
    private String locale;

    public static RestPlayer from(Player player) {
        return new RestPlayer(
                player.getName(),
                player.getUuid(),
                player.getType().toString(),
                RestLocation.from(player.getLocation()),
                player.getGameMode().name(),
                RestPlayerAbilities.from(player.getAbilities()),
                player.customName(),
                player.getHealth(),
                player.hasAI(),
                RestInventory.from(player.getInventory()),
                player.getEffects().isEmpty() ?
                        null :
                        player
                                .getEffects()
                                .stream()
                                .map(RestPotionEffect::from)
                                .collect(Collectors.toList()),
                player.getLocale().toLanguageTag()
        );
    }

}
