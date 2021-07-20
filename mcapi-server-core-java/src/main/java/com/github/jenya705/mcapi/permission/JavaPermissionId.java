package com.github.jenya705.mcapi.permission;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Embeddable;

/**
 * @author Jenya705
 */
@Embeddable
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class JavaPermissionId {

    @NotNull
    private String name;

    @Nullable
    private String target;

    @NotNull
    private String token;

    public static JavaPermissionId target(String name, String target, String token) {
        return new JavaPermissionId(name, target, token);
    }

    public static JavaPermissionId global(String name, String token) {
        return new JavaPermissionId(name, null, token);
    }

}
