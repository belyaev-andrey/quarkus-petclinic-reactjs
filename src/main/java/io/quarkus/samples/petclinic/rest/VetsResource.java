package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Specialty;
import io.quarkus.samples.petclinic.model.Vet;
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

@Path("api/vets")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class VetsResource {

    @Inject
    ClinicService clinicService;

    @GET
    @RolesAllowed(Roles.VET_ADMIN)
    public Response getAllSpecialtys() {
        Collection<Vet> vets = clinicService.findAllVets();
        if (vets.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(vets).build();
    }

    @GET
    @Path("/{vetId}")
    @RolesAllowed(Roles.VET_ADMIN)
    public Response getVet(@PathParam("vetId") int vetId) {
        Vet vet = clinicService.findVetById(vetId);
        if (vet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(vet).build();
    }

    @POST
    @RolesAllowed(Roles.VET_ADMIN)
    public Response addVet(@Valid Vet vet) {
        vet = clinicService.saveVet(vet);
        URI uri = URI.create(String.format("api/vets/%s", vet.getId()));
        return Response.ok(vet).location(uri).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{vetId}")
    @RolesAllowed(Roles.VET_ADMIN)
    public Response updateVet(@PathParam("vetId") int vetId, @Valid Vet vet) {
        Vet currentVet = clinicService.findVetById(vetId);
        if (currentVet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentVet.setFirstName(vet.getFirstName());
        currentVet.setLastName(vet.getLastName());
        currentVet.clearSpecialties();
        for (Specialty spec : vet.getSpecialties()) {
            currentVet.addSpecialty(spec);
        }
        clinicService.saveVet(currentVet);
        return Response.ok(currentVet).status(Response.Status.NO_CONTENT).build();
    }


    @DELETE
    @Path("/{vetId}")
    @RolesAllowed(Roles.VET_ADMIN)
    public Response deleteSpecialty(@PathParam("vetId") int vetId) {
        Vet vet = clinicService.findVetById(vetId);
        if (vet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        clinicService.deleteVet(vet);
        return Response.noContent().build();
    }


}
