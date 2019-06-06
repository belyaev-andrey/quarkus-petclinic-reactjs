package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.PetType;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
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
import java.util.ArrayList;
import java.util.Collection;

@Path("api/pettypes")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PetTypesResource {

    @Inject
    ClinicService clinicService;

    @GET
    public Response getAllPetTypes(){
        Collection<PetType> petTypes = new ArrayList<>(this.clinicService.findAllPetTypes());
        if (petTypes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(petTypes).build();
    }

    @GET
    @Path("/{petTypeId}")
    public Response getPetType(@PathParam("petTypeId") int petTypeId){
        PetType petType = clinicService.findPetTypeById(petTypeId);
        if(petType == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(petType).build();
    }

    @POST
    public Response addPetType(@Valid PetType petType){
        this.clinicService.savePetType(petType);
        URI uri = URI.create(String.format("/api/pettypes/%s", petType.getId()));
        return Response.ok(petType).location(uri).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{petTypeId}")
    public Response updatePetType(@PathParam("petTypeId") int petTypeId, @Valid PetType petType){
        PetType currentPetType = this.clinicService.findPetTypeById(petTypeId);
        if(currentPetType == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentPetType.setName(petType.getName());
        this.clinicService.savePetType(currentPetType);
        return Response.ok(currentPetType).status(Response.Status.NO_CONTENT).build();
    }


    @DELETE
    @Path("/{petTypeId}")
    public Response deletePetType(@PathParam("petTypeId") int petTypeId){
        PetType petType = this.clinicService.findPetTypeById(petTypeId);
        if(petType == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.clinicService.deletePetType(petType);
        return Response.noContent().build();
    }


}
