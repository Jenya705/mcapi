package com.github.jenya705.mcapi.rest;

import com.github.jenya705.mcapi.BoundingBox;
import com.github.jenya705.mcapi.Vector3;
import lombok.*;

import java.util.Objects;

/**
 * @author Jenya705
 */
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RestBoundingBox)) return false;
        if (this == obj) return true;
        RestBoundingBox other = (RestBoundingBox) obj;
        return Objects.equals(asBoundingBox(), other.asBoundingBox());
    }

    public BoundingBox asBoundingBox() {
        return new BoundingBox(
                Vector3.of(x0, y0, z0),
                Vector3.of(x1, y1, z1)
        );
    }

}
