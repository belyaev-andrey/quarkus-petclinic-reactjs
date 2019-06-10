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

@Path("api")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class VisitsResource {

    @Inject
    ClinicService clinicService;

    @POST
    @Path("/owners/{ownerId}/pets/{petId}/visits")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response addVisit(@PathParam("petId") int petId, @Valid Visit visit){
        final Pet pet = clinicService.findPetById(petId);
        if (pet == null) {
            return Response.status(404).build();
        }
        pet.addVisit(visit);
        clinicService.savePet(pet);
        return Response.noContent().build();
    }
}
