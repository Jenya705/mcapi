package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.Beacon;
import com.github.jenya705.mcapi.entity.LivingEntity;
import com.github.jenya705.mcapi.jackson.DefaultInteger;
import com.github.jenya705.mcapi.jackson.DefaultNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBeacon {

    @DefaultNull
    private List<UUID> affectedEntities;
    @DefaultNull
    private String primaryEffect;
    @DefaultNull
    private String secondaryEffect;
    @DefaultInteger(0)
    private int tier;

    public static RestBeacon from(Beacon beacon) {
        return new RestBeacon(
                beacon
                        .getAffectedEntities()
                        .stream()
                        .map(LivingEntity::getUuid)
                        .collect(Collectors.toList()),
                beacon.getPrimaryEffect() == null ? null : beacon.getPrimaryEffect().getKey().toString(),
                beacon.getSecondaryEffect() == null ? null : beacon.getSecondaryEffect().getKey().toString(),
                beacon.getTier()
        );
    }

}
