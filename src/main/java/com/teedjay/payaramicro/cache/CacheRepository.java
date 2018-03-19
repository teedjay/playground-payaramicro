package com.teedjay.payaramicro.cache;

import javax.cache.annotation.*;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

@ApplicationScoped
@CacheDefaults(cacheName = "MyCache")
public class CacheRepository {

    @CacheResult
    public String getItemByUuid(String uuid) {
        return "The data for '" + uuid + "' was cached at " + LocalDateTime.now().toString();
    }

    @CacheRemove
    public String removeItemByUuid(String uuid) {
        return "The cached entry '" + uuid + "' was removed at " + LocalDateTime.now().toString();
    }

    @CacheRemoveAll
    public String removeAllItemsFromCache() {
        return "The cached was completely emptied at " + LocalDateTime.now().toString();
    }

}
