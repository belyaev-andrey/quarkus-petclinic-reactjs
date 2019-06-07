package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Visit;
import io.quarkus.samples.petclinic.security.Roles;
import io.quarkus.samples.petclinic.service.ClinicService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
import java.util.Collection;

@Path("api/visits")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class VisitsResource {

    @Inject
    ClinicService clinicService;

    @GET
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response getAllVisits(){
        Collection<Visit> visits = clinicService.findAllVisits();
        if (visits.isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(visits).build();
    }

    @GET
    @Path("/{visitId}")
    public Response getVisit(@PathParam("visitId") int visitId){
        Visit visit = clinicService.findVisitById(visitId);
        if(visit == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(visit).build();
    }

    @POST
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response addVisit(@Valid Visit visit){
        visit = clinicService.saveVisit(visit);
        URI uri = URI.create(String.format("/api/visits/%s", visit.getId()));
        return Response.ok(visit).location(uri).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{visitId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response updateVisit(@PathParam ("visitId") int visitId, @Valid Visit visit){
        Visit currentVisit = this.clinicService.findVisitById(visitId);
        if(currentVisit == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentVisit.setDate(visit.getDate());
        currentVisit.setDescription(visit.getDescription());
        currentVisit.setPet(visit.getPet());
        visit = clinicService.saveVisit(currentVisit);
        return Response.ok(visit).status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{visitId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response deleteVisit(@PathParam ("visitId") int visitId){
        Visit visit = this.clinicService.findVisitById(visitId);
        if(visit == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        this.clinicService.deleteVisit(visit);
        return Response.noContent().build();
    }


}
