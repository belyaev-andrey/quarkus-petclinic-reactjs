package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.PetType;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("api/pets")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PetResource {

    @Inject
    ClinicService clinicService;

    @GET
    @Path("/pettypes")
    public Collection<PetType> hello() {
        return clinicService.findAllPetTypes();
    }
}