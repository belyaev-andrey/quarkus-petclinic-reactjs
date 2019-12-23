package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Pet;
import io.quarkus.samples.petclinic.model.Visit;
import io.quarkus.samples.petclinic.security.Roles;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("api")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class VisitsResource {

    @Inject
    ClinicService clinicService;

    @POST
    @Path("/owners/{ownerId}/pets/{petId}/visits")
    @RolesAllowed({Roles.OWNER_ADMIN, Roles.VET_ADMIN})
    public Response addVisit(@PathParam("petId") long petId, @Valid Visit visit){
        Pet pet = clinicService.addVisit(petId, visit);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        URI uri = URI.create(String.format("/owners/%s/pets/%s/visits", pet.getOwner().getId(), pet.getId()));
        return Response.status(Response.Status.CREATED).entity(pet).location(uri).build();
    }
}
