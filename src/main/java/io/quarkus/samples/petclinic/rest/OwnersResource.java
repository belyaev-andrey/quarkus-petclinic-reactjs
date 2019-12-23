package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Owner;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

@Path("/api")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class OwnersResource {

    @Inject
    ClinicService clinicService;

    @POST
    @Path("/owner")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response addOwner(@Valid Owner owner) {
        owner = clinicService.saveOwner(owner);
        URI uri = URI.create(String.format("/api/owner/%s", owner.getId()));
        return Response.ok(owner).location(uri).build();
    }

    @GET
    @Path("/owner/{ownerId}")
    @RolesAllowed({Roles.OWNER_ADMIN, Roles.VET_ADMIN})
    public Response getOwner(@PathParam("ownerId") long ownerId) {
        Owner owner = clinicService.findOwnerById(ownerId);
        if (owner == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(owner).build();
    }


    @GET
    @Path("/owner/list")
    @RolesAllowed({Roles.OWNER_ADMIN, Roles.VET_ADMIN})
    public Response getOwnersList(@QueryParam("lastName") String ownerLastName) {
        if (ownerLastName == null) {
            ownerLastName = "";
        }
        Collection<Owner> owners = clinicService.findOwnerByLastName(ownerLastName);
        return Response.ok(owners).build();
    }

    @PUT
    @Path("/owner/{ownerId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response updateOwner(@PathParam("ownerId") long ownerId, @Valid Owner owner) {
        owner = clinicService.updateOwner(ownerId, owner);
        if (owner == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        URI uri = URI.create(String.format("/api/owner/%s", owner.getId()));
        return Response.ok(owner).location(uri).build();
    }

}
