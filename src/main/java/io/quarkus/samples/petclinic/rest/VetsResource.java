package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Vet;
import io.quarkus.samples.petclinic.security.Roles;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("api")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class VetsResource {

    @Inject
    ClinicService clinicService;

    @GET
    @Path("/vets")
    @RolesAllowed(Roles.VET_ADMIN)
    public Response getAllSpecialties() {
        Collection<Vet> vets = clinicService.findAllVets();
        if (vets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(vets).build();
    }
}
