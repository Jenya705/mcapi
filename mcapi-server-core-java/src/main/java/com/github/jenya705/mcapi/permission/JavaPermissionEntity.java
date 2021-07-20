package com.github.jenya705.mcapi.permission;

import com.github.jenya705.mcapi.ApiPermission;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Jenya705
 */
@Entity
@Data
@Table(name = "mcapi_permission")
public class JavaPermissionEntity implements ApiPermission {

    @EmbeddedId
    @Column
    private JavaPermissionId id;

    @Column
    private boolean enabled;

    @Override
    public @NotNull String getName() {
        return getId().getName();
    }

    @Override
    public @Nullable String getTarget() {
        return getId().getTarget();
    }

    @Override
    public @NotNull String getToken() {
        return getId().getToken();
    }
}
