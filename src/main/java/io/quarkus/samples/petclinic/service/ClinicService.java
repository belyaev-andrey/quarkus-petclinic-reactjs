package io.quarkus.samples.petclinic.service;

import io.quarkus.samples.petclinic.model.PetType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
}
