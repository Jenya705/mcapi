package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.BoundingBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jenya705
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBoundingBox {

    private double x0;
    private double y0;
    private double z0;
    private double x1;
    private double y1;
    private double z1;

    public static RestBoundingBox from(BoundingBox boundingBox) {
        return new RestBoundingBox(
                boundingBox.getMinCorner().getX(),
                boundingBox.getMinCorner().getY(),
                boundingBox.getMinCorner().getZ(),
                boundingBox.getMaxCorner().getX(),
                boundingBox.getMaxCorner().getY(),
                boundingBox.getMaxCorner().getZ()
        );
    }

}
