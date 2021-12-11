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
public class RestShortBoundingBox {

    private double widthX;
    private double widthZ;
    private double height;

    public static RestShortBoundingBox from(BoundingBox boundingBox) {
        return new RestShortBoundingBox(
                boundingBox.getWidthX(),
                boundingBox.getWidthZ(),
                boundingBox.getHeight()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RestShortBoundingBox)) return false;
        if (obj == this) return true;
        RestShortBoundingBox other = (RestShortBoundingBox) obj;
        return Objects.equals(asVector(), other.asVector());
    }

    public Vector3 asVector() {
        return new Vector3(
                widthX,
                height,
                widthZ
        );
    }

}
