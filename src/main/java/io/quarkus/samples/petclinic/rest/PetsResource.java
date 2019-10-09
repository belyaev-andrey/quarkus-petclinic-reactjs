package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Owner;
import io.quarkus.samples.petclinic.model.Pet;
import io.quarkus.samples.petclinic.model.PetType;
import io.quarkus.samples.petclinic.security.Roles;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("api")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PetsResource {

    @Inject
    ClinicService clinicService;

    @GET
    @Path("/pettypes")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Collection<PetType> getPetTypes() {
        return clinicService.findAllPetTypes();
    }

    @POST
    @Path("/owners/{ownerId}/pets")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response addPet(@PathParam("ownerId") int ownerId, @Valid Pet pet) {
        Owner owner = this.clinicService.findOwnerById(ownerId);
        if (owner == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        owner.addPet(pet);
        pet = clinicService.savePet(pet);
        return Response.ok(pet).status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/owners/{ownerId}/pets/{petId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response updatePet(@PathParam("petId") int petId, @Valid Pet pet) {
        Pet currentPet = clinicService.findPetById(petId);
        if (currentPet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentPet.setBirthDate(pet.getBirthDate());
        currentPet.setName(pet.getName());
        currentPet.setType(pet.getType());
        pet = clinicService.savePet(currentPet);
        return Response.ok(pet).status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/owners/{ownerId}/pets/{petId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response getPet(@PathParam("petId") int petId) {
        Pet pet = clinicService.findPetById(petId);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pet).build();
    }
}
