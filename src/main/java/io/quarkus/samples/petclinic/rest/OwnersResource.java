package io.quarkus.samples.petclinic.rest;

import io.quarkus.samples.petclinic.model.Owner;
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
import java.util.Collection;

@Path("/api/owners")
@Produces(MediaTypes.APPLICATION_JSON_UTF8)
@Consumes(MediaTypes.APPLICATION_JSON_UTF8)
public class OwnersResource {

    @Inject
    ClinicService clinicService;

    @GET
    @Path("/*/lastname/{lastName}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response getOwnersList(@PathParam("lastName") String ownerLastName) {
        if (ownerLastName == null) {
            ownerLastName = "";
        }
        Collection<Owner> owners = clinicService.findOwnerByLastName(ownerLastName);
        if (owners.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(owners).build();
    }


    @GET
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response getOwners() {
        Collection<Owner> owners = clinicService.findAllOwners();
        if (owners.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(owners).build();
    }

    @GET
    @Path("/{ownerId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response getOwner(@PathParam("ownerId") int ownerId) {
        Owner owner = null;
        owner = clinicService.findOwnerById(ownerId);
        if (owner == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(owner).build();
    }

    @POST
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response addOwner(@Valid Owner owner) {
        owner = clinicService.saveOwner(owner);
        URI uri = URI.create(String.format("/api/owners/%s", owner.getId()));
        return Response.ok(owner).location(uri).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{ownerId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response updateOwner(@PathParam("ownerId") int ownerId, @Valid Owner owner) {
        Owner currentOwner = clinicService.findOwnerById(ownerId);
        if (currentOwner == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        currentOwner.setAddress(owner.getAddress());
        currentOwner.setCity(owner.getCity());
        currentOwner.setFirstName(owner.getFirstName());
        currentOwner.setLastName(owner.getLastName());
        currentOwner.setTelephone(owner.getTelephone());
        clinicService.saveOwner(currentOwner);
        return Response.ok(currentOwner).status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{ownerId}")
    @RolesAllowed(Roles.OWNER_ADMIN)
    public Response deleteOwner(@PathParam("ownerId") int ownerId) {
        Owner owner = clinicService.findOwnerById(ownerId);
        if (owner == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        clinicService.deleteOwner(owner);
        return Response.noContent().build();
    }


}
