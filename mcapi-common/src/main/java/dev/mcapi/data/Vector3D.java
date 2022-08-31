package dev.mcapi.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

// TODO change to better Vector3D
@Getter
@With
@RequiredArgsConstructor(staticName = "of")
public class Vector3D {

    public static Vector3D zero() {
        return new Vector3D(0, 0, 0);
    }

    private final float x;
    private final float y;
    private final float z;

}
