package com.github.jenya705.mcapi.rest.block;

import com.github.jenya705.mcapi.block.data.Piston;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestPiston {

    private RestBlock block;
    private String direction;
    private boolean extended;

    public static RestPiston from(Piston piston) {
        return new RestPiston(
                RestBlock.from(piston.getBlock()),
                piston.getDirection().name(),
                piston.isExtended()
        );
    }

}
