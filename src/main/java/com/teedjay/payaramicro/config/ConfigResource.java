package com.teedjay.payaramicro.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.cache.annotation.CacheRemove;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("config")
@RequestScoped
public class ConfigResource {

    // config priority : system -> environment -> META-INF/microprofile-config.properties

    @Inject
    @ConfigProperty(name = "some.cool.username", defaultValue = "DefaultUsernameFromCode")
    private String username;

    @GET
    @CacheRemove
    public String returnTheInjectedProperty() {
        return "The injected username string is : '" + username + "'\n";
    }

}
