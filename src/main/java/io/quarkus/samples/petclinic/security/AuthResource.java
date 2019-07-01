package io.quarkus.samples.petclinic.security;

import io.quarkus.samples.petclinic.rest.MediaTypes;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/auth")
@PermitAll
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class AuthResource {

    @POST
    public Response authorize(@Context SecurityContext sc) {
        Principal userPrincipal = sc.getUserPrincipal();
        if (userPrincipal != null) {
            return Response.status(Response.Status.ACCEPTED).entity(userPrincipal).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
