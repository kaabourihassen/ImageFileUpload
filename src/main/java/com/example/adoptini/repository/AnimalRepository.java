package com.example.adoptini.repository;


import com.example.adoptini.model.Animal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly= true)
public interface AnimalRepository extends MongoRepository<Animal, ObjectId> {

    Optional<Animal> findById(ObjectId id);

    void deleteById(ObjectId animalId);
    Animal save(Animal animal);
}
