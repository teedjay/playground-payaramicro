package com.teedjay.payaramicro.cache;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("cache")
@ApplicationScoped
public class CacheResource {

    @Inject
    private CacheRepository cacheRepository;

    @GET
    @Path("{uuid}")
    public String getItemThatMightBeCached(@PathParam("uuid") String uuid) {
        return cacheRepository.getItemByUuid(uuid);
    }

    @DELETE
    @Path("{uuid}")
    public String removeItemFromCache(@PathParam("uuid") String uuid) {
        return cacheRepository.removeItemByUuid(uuid);
    }

    @DELETE
    @Path("all")
    public String removeAllItemsFromCache() {
        return cacheRepository.removeAllItemsFromCache();
    }

}
