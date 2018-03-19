package com.teedjay.payaramicro.security;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.util.Base64;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
@RequestScoped
public class BasicAuthenticationLayer implements ContainerRequestFilter {

    @Inject
    private AuthenticatedUser authenticatedUser;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String path = containerRequestContext.getUriInfo().getPath();
        if (path.startsWith("secure")) {
            String authHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            if (authHeader == null) throw new NotAuthorizedException("Missing authentication for " + path);
            if (!authHeader.startsWith("Basic ")) throw new NotAuthorizedException("This resource need basic authentication");
            authHeader = authHeader.substring(6); // drop "Basic " from the header value
            String[] basicAuth = new String(Base64.getDecoder().decode(authHeader)).split(":");
            if (basicAuth.length != 2) throw new NotAuthorizedException("No pre-emptive basic authentication found");
            authenticatedUser.setUsername(basicAuth[0]);
            authenticatedUser.setRoles("user,superuser,customer");
        }
    }

}
