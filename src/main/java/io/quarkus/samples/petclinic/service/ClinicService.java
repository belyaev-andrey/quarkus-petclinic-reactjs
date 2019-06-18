package io.quarkus.samples.petclinic.service;

import io.quarkus.samples.petclinic.model.Owner;
import io.quarkus.samples.petclinic.model.Pet;
import io.quarkus.samples.petclinic.model.PetType;
import io.quarkus.samples.petclinic.model.Specialty;
import io.quarkus.samples.petclinic.model.Vet;
import io.quarkus.samples.petclinic.model.Visit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
@Transactional
public class ClinicService {

    @Inject
    EntityManager em;

    public List<PetType> findAllPetTypes() {
        return em.createNamedQuery("PetTypes.findAll", PetType.class).getResultList();
    }

    public PetType findPetTypeById(Integer petTypeId) {
        return em.find(PetType.class, petTypeId);
    }

    public Collection<Vet> findAllVets() {
        return em.createNamedQuery("Vets.findAll", Vet.class).getResultList();
    }

    public Pet findPetById(Integer petId) {
        return em.find(Pet.class, petId);
    }

    public Pet savePet(Pet pet) {
        pet = em.merge(pet);
        em.persist(pet);
        return pet;
    }

    public Collection<Owner> findOwnerByLastName(String ownerLastName) {
        return em.createNamedQuery("Owners.findByLastName", Owner.class)
                .setParameter("lastName", ownerLastName)
                .getResultList();
    }

    public Owner findOwnerById(Integer ownerId) {
        return em.find(Owner.class, ownerId);
    }


    public Owner saveOwner(Owner owner) {
        owner = em.merge(owner);
        em.persist(owner);
        return owner;
    }
}
