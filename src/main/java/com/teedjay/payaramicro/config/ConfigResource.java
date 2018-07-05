package com.teedjay.payaramicro.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/config")
@RequestScoped
public class ConfigResource {

    // config priority : system -> environment -> CustomConfigSource -> META-INF/microprofile-config.properties

    @Inject
    @ConfigProperty(name = "some.cool.username", defaultValue = "DefaultUsernameFromCode")
    private String username;

    @Inject
    @ConfigProperty(name = "cheese.better", defaultValue = "DefaultFavoriteCheese")
    private String cheese;

    @GET
    public String returnTheInjectedProperty() {
        return String.format("The injected username string is '%s' and favorite cheese is '%s'%n", username, cheese);
    }

}
