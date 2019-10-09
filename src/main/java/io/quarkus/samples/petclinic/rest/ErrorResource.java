package io.quarkus.samples.petclinic.rest;

import javax.ws.rs.*;
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
