package com.github.jenya705.mcapi.token;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 * @since 1.0
 * @author Jenya705
 */
public interface ApiServerTokenRepository {

    void save(ApiServerTokenHolderEntity holder);

    void saveAll(Collection<ApiServerTokenHolderEntity> holders);

    ApiServerTokenHolderEntity getHolderByToken(String token);

    List<ApiServerTokenHolderEntity> getHoldersByUUID(UUID player);

}
