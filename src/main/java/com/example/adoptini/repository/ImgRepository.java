package com.example.adoptini.repository;

import com.example.adoptini.model.Img;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImgRepository extends MongoRepository<Img, ObjectId> {
}
