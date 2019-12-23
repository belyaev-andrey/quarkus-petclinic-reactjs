package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Owner;
import io.quarkus.samples.petclinic.model.Pet;
import io.quarkus.samples.petclinic.security.Roles;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("api")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class PetsResource {

    @Inject
    ClinicService clinicService;

    @POST
    @Path("/owners/{ownerId}/pets")
    @RolesAllowed({Roles.OWNER_ADMIN, Roles.VET_ADMIN})
    public Response addPet(@PathParam("ownerId") long ownerId, @Valid Pet pet) {
        Owner owner = this.clinicService.findOwnerById(ownerId);
        if (owner == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        owner.addPet(pet);
        pet = clinicService.savePet(pet);
        URI uri = URI.create(String.format("/owners/%s/pets", owner.getId()));
        return Response.status(Response.Status.CREATED).entity(pet).location(uri).build();
    }

    @PUT
    @Path("/owners/{ownerId}/pets/{petId}")
    @RolesAllowed({Roles.OWNER_ADMIN, Roles.VET_ADMIN})
    public Response updatePet(@PathParam("petId") long petId, @Valid Pet pet) {
        pet = clinicService.updatePet(petId, pet);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        URI uri = URI.create(String.format("/owners/%s/pets/%s", pet.getOwner().getId(), pet.getId()));
        return Response.ok(pet).location(uri).build();
    }

    @GET
    @Path("/owners/{ownerId}/pets/{petId}")
    @RolesAllowed({Roles.OWNER_ADMIN, Roles.VET_ADMIN})
    public Response getPet(@PathParam("petId") long petId) {
        Pet pet = clinicService.findPetById(petId);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pet).build();
    }
}
