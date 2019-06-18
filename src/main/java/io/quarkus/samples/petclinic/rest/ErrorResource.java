package io.quarkus.samples.petclinic.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class ErrorResource {

    @GET
    @Path("/oups")
    public Response error() {
        throw new WebApplicationException("Expected: controller used to showcase what " +
                "happens when an exception is thrown");
    }


}
