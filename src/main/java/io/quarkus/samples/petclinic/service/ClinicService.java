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

    public PetType savePetType(PetType petType) {
        petType = em.merge(petType);
        em.persist(petType);
        return petType;
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

    public Specialty saveSpecialty(Specialty specialty) {
        specialty = em.merge(specialty);
        em.persist(specialty);
        return specialty;
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

    public Vet saveVet(Vet vet) {
        vet = em.merge(vet);
        em.persist(vet);
        return vet;
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

    public Pet savePet(Pet pet) {
        pet = em.merge(pet);
        em.persist(pet);
        return pet;
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


    public Owner saveOwner(Owner owner) {
        owner = em.merge(owner);
        em.persist(owner);
        return owner;
    }

    public void deleteOwner(Owner owner) {
        em.remove(em.merge(owner));
    }

    public Collection<Visit> findAllVisits() {
        return em.createNamedQuery("Visits.findAll", Visit.class).getResultList();
    }

    public Visit findVisitById(Integer visitId) {
        return em.find(Visit.class, visitId);
    }

    public Visit saveVisit(Visit visit) {
        visit = em.merge(visit);
        em.persist(visit);
        return visit;
    }

    public void deleteVisit(Visit visit) {
        em.remove(em.merge(visit));
    }
}
