package io.quarkus.samples.petclinic.service;

import io.quarkus.panache.common.Sort;
import io.quarkus.samples.petclinic.model.Owner;
import io.quarkus.samples.petclinic.model.Pet;
import io.quarkus.samples.petclinic.model.PetType;
import io.quarkus.samples.petclinic.model.Vet;
import io.quarkus.samples.petclinic.model.Visit;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
@Transactional
public class ClinicService {

    public List<PetType> findAllPetTypes() {
        return PetType.listAll();
    }

    public PetType findPetTypeById(Long petTypeId) {
        return PetType.findById(petTypeId);
    }

    public Collection<Vet> findAllVets() {
        return Vet.findAll(Sort.ascending("firstName")).list();
    }

    public Pet findPetById(Long petId) {
        return Pet.findById(petId);
    }

    public Pet savePet(Pet pet) {
        pet.persist();
        return pet;
    }

    public Pet addVisit(long petId, Visit visit) {
        Pet currentPet = findPetById(petId);
        if (currentPet == null) {
            return null;
        }
        currentPet.addVisit(visit);
        return currentPet;
    }


    public Pet updatePet(long petId, Pet pet) {
        Pet currentPet = findPetById(petId);
        if (currentPet == null) {
            return null;
        }
        currentPet.setBirthDate(pet.getBirthDate());
        currentPet.setName(pet.getName());
        currentPet.setType(pet.getType());
        return currentPet;
    }


    public Collection<Owner> findOwnerByLastName(String ownerLastName) {
        return Owner.findByLastName(ownerLastName);
    }

    public Owner findOwnerById(Long ownerId) {
        return Owner.findById(ownerId);
    }


    public Owner saveOwner(Owner owner) {
        owner.persist();
        return owner;
    }

    public Owner updateOwner(long ownerId, Owner owner) {
        Owner currentOwner = findOwnerById(ownerId);
        currentOwner.setAddress(owner.getAddress());
        currentOwner.setCity(owner.getCity());
        currentOwner.setFirstName(owner.getFirstName());
        currentOwner.setLastName(owner.getLastName());
        currentOwner.setTelephone(owner.getTelephone());
        return currentOwner;
    }


}
