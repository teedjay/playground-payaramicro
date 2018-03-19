package com.teedjay.payaramicro.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("secure")
@RequestScoped
public class SecureResource {

    @Inject
    private AuthenticatedUser authenticatedUser;

    @GET
    public String returnUsernameAndRoles() {
        return "AuthenticatedUser{" +
            "username='" + authenticatedUser.getUsername() + '\'' +
            ", role='" + authenticatedUser.getRoles() + '\'' +
            '}';
    }

}
