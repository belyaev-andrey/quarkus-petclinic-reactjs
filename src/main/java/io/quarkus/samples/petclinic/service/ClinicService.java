package io.quarkus.samples.petclinic.service;

import io.quarkus.samples.petclinic.model.Owner;
import io.quarkus.samples.petclinic.model.Pet;
import io.quarkus.samples.petclinic.model.PetType;
import io.quarkus.samples.petclinic.model.Specialty;
import io.quarkus.samples.petclinic.model.Vet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
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

    public void savePetType(PetType petType) {
        em.persist(em.merge(petType));
    }

    public void deletePetType(PetType petType) {
        em.remove(em.merge(petType));
    }

    public Collection<Specialty> findAllSpecialties() {
        return em.createNamedQuery("Specialties.findAll", Specialty.class).getResultList();
    }

    public Specialty findSpecialtyById(Integer specialtyId) {
        return em.find(Specialty.class, specialtyId);
    }

    public void saveSpecialty(Specialty specialty) {
        em.persist(em.merge(specialty));
    }

    public void deleteSpecialty(Specialty specialty) {
        em.remove(em.merge(specialty));
    }

    public Collection<Vet> findAllVets() {
        return em.createNamedQuery("Vets.findAll", Vet.class).getResultList();
    }

    public Vet findVetById(Integer vetId) {
        return em.find(Vet.class, vetId);
    }

    public void saveVet(Vet vet) {
        em.persist(em.merge(vet));
    }

    public void deleteVet(Vet vet) {
        em.remove(em.merge(vet));
    }

    public Pet findPetById(Integer petId) {
        return em.find(Pet.class, petId);
    }

    public Collection<Pet> findAllPets() {
        return em.createNamedQuery("Pets.findAll", Pet.class).getResultList();
    }

    public void savePet(Pet pet) {
        em.persist(em.merge(pet));
    }

    public void deletePet(Pet pet) {
        em.remove(em.merge(pet));
    }

    public Collection<Owner> findOwnerByLastName(String ownerLastName) {
        return em.createNamedQuery("Owners.findByLastName", Owner.class)
                .setParameter("lastName", ownerLastName)
                .getResultList();
    }

    public Collection<Owner> findAllOwners() {
        return em.createNamedQuery("Owners.findAll", Owner.class)
                .getResultList();
    }

    public Owner findOwnerById(Integer ownerId) {
        return em.find(Owner.class, ownerId);
    }


    public void saveOwner(Owner owner) {
        em.persist(em.merge(owner));
    }

    public void deleteOwner(Owner owner) {
        em.remove(em.merge(owner));
    }
}
