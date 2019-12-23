package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.PetType;
import io.quarkus.samples.petclinic.security.Roles;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("api/pettypes")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class PetTypesResource {

    @Inject
    ClinicService clinicService;

    @GET
    @RolesAllowed({Roles.OWNER_ADMIN, Roles.VET_ADMIN})
    public Response getAllPetTypes(){
        Collection<PetType> petTypes = clinicService.findAllPetTypes();
        if (petTypes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(petTypes).build();
    }

    @GET
    @Path("/{petTypeId}")
    @RolesAllowed({Roles.OWNER_ADMIN, Roles.VET_ADMIN})
    public Response getPetType(@PathParam("petTypeId") long petTypeId){
        PetType petType = clinicService.findPetTypeById(petTypeId);
        if(petType == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(petType).build();
    }
}
