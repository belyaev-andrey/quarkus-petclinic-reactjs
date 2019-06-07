package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Specialty;
import io.quarkus.samples.petclinic.security.Roles;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

@Path("api/specialities")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class SpecialtiesResource {

    @Inject
    ClinicService clinicService;

    @GET
    @RolesAllowed(Roles.VET_ADMIN)
    public Response getAllSpecialtys(){
        Collection<Specialty> specialties = clinicService.findAllSpecialties();
        if (specialties.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(specialties).build();
    }

    @GET
    @Path("/{specialtyId}")
    @RolesAllowed(Roles.VET_ADMIN)
    public Response getSpecialty(@PathParam("specialtyId") int specialtyId){
        Specialty specialty = clinicService.findSpecialtyById(specialtyId);
        if(specialty == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(specialty).build();
    }

    @POST
    @RolesAllowed(Roles.VET_ADMIN)
    public Response addSpecialty(@Valid Specialty specialty){
        specialty = clinicService.saveSpecialty(specialty);
        URI uri = URI.create(String.format("api/specialities/%s", specialty.getId()));
        return Response.ok(specialty).location(uri).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{specialtyId}")
    @RolesAllowed(Roles.VET_ADMIN)
    public Response updateSpecialty(@PathParam("specialtyId") int specialtyId, @Valid Specialty specialty){
        Specialty currentSpecialty = clinicService.findSpecialtyById(specialtyId);
        if(currentSpecialty == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentSpecialty.setName(specialty.getName());
        clinicService.saveSpecialty(currentSpecialty);
        return Response.ok(currentSpecialty).status(Response.Status.NO_CONTENT).build();
    }


    @DELETE
    @Path("/{specialtyId}")
    @RolesAllowed(Roles.VET_ADMIN)
    public Response deleteSpecialty(@PathParam("specialtyId") int specialtyId){
        Specialty specialty = clinicService.findSpecialtyById(specialtyId);
        if(specialty == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        clinicService.deleteSpecialty(specialty);
        return Response.noContent().build();
    }


}
