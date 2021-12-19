package com.example.adoptini.repository;


import com.example.adoptini.model.Animal;
import com.example.adoptini.model.Like;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends MongoRepository<Like, ObjectId> {

    List<Like> findByAnimal(Animal animal);
}
