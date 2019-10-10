package io.quarkus.samples.petclinic.security;

import io.quarkus.samples.petclinic.rest.MediaTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("api/auth")
@PermitAll
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class AuthResource {

    private static final Logger log = LoggerFactory.getLogger("AuthResource");

    @POST
    public Response authorize(@Context SecurityContext sc) {
        Principal userPrincipal = sc.getUserPrincipal();

        log.info("Security context: "+sc);

        if (userPrincipal != null) {
            return Response.status(Response.Status.ACCEPTED).entity(userPrincipal).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
