package io.quarkus.samples.petclinic.service;

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
public class ClinicService {

    @Inject
    EntityManager em;

    @Transactional
    public List<PetType> findAllPetTypes() {
        return em.createNamedQuery("PetTypes.findAll", PetType.class).getResultList();
    }

    @Transactional
    public PetType findPetTypeById(int petTypeId) {
        return em.find(PetType.class, petTypeId);
    }

    @Transactional
    public void savePetType(PetType petType) {
        em.persist(em.merge(petType));
    }

    @Transactional
    public void deletePetType(PetType petType) {
        em.remove(em.merge(petType));
    }

    @Transactional
    public Collection<Specialty> findAllSpecialties() {
        return em.createNamedQuery("Specialties.findAll", Specialty.class).getResultList();
    }

    @Transactional
    public Specialty findSpecialtyById(Integer specialtyId) {
        return em.find(Specialty.class, specialtyId);
    }

    @Transactional
    public void saveSpecialty(Specialty specialty) {
        em.persist(em.merge(specialty));
    }

    @Transactional
    public void deleteSpecialty(Specialty specialty) {
        em.remove(em.merge(specialty));
    }

    @Transactional
    public Collection<Vet> findAllVets() {
        return em.createNamedQuery("Vets.findAll", Vet.class).getResultList();
    }

    @Transactional
    public Vet findVetById(Integer vetId) {
        return em.find(Vet.class, vetId);
    }

    @Transactional
    public void saveVet(Vet vet) {
        em.persist(em.merge(vet));
    }

    @Transactional
    public void deleteVet(Vet vet) {
        em.remove(em.merge(vet));
    }
}
