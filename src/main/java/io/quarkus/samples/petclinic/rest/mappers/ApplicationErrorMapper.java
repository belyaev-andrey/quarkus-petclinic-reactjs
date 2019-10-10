package io.quarkus.samples.petclinic.rest.mappers;

import io.quarkus.samples.petclinic.rest.MediaTypes;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ApplicationErrorMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", Response.Status.INTERNAL_SERVER_ERROR);
        result.put("message", exception.getMessage());

        return Response.serverError().type(MediaTypes.APPLICATION_JSON_UTF8_TYPE).entity(result).build();
    }
}
