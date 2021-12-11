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

}
