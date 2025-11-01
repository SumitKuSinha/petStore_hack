package com.hackathon.petstore_cms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hackathon.petstore_cms.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {

	List<Pet> findBySpecies(String species);
	// You'll need to import org.springframework.data.jpa.repository.Query
	@Query("SELECT p FROM Pet p WHERE p.name LIKE %?1% OR p.species LIKE %?1% OR p.breed LIKE %?1%")
	List<Pet> search(String keyword);
}