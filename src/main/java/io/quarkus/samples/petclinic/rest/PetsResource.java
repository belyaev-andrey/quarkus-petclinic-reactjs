package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Pet;
import io.quarkus.samples.petclinic.model.PetType;
import io.quarkus.samples.petclinic.security.Roles;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

@Path("api/pets")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PetsResource {

    @Inject
    ClinicService clinicService;

    @GET
    @Path("/{petId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response getPet(@PathParam("petId") int petId) {
        Pet pet = clinicService.findPetById(petId);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pet).build();
    }

    @GET
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response getPets() {
        Collection<Pet> pets = clinicService.findAllPets();
        if (pets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pets).build();
    }

    @GET
    @Path("/pettypes")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Collection<PetType> getPetTypes() {
        return clinicService.findAllPetTypes();
    }

    @POST
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response addPet(@Valid Pet pet) {
        pet = clinicService.savePet(pet);
        URI uri = URI.create(String.format("/api/pets/%s", pet.getId()));
        return Response.ok(pet).location(uri).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{petId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response updatePet(@PathParam("petId") int petId, @Valid Pet pet) {
        Pet currentPet = clinicService.findPetById(petId);
        if (currentPet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentPet.setBirthDate(pet.getBirthDate());
        currentPet.setName(pet.getName());
        currentPet.setType(pet.getType());
        currentPet.setOwner(pet.getOwner());
        clinicService.savePet(currentPet);
        return Response.ok(currentPet).status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{petId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response deletePet(@PathParam("petId") int petId) {
        Pet pet = clinicService.findPetById(petId);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        clinicService.deletePet(pet);
        return Response.noContent().build();
    }


}
