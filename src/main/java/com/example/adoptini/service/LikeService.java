package com.example.adoptini.service;

import com.example.adoptini.model.Animal;
import com.example.adoptini.model.Like;
import com.example.adoptini.model.User;
import com.example.adoptini.repository.AnimalRepository;
import com.example.adoptini.repository.LikeRepository;
import com.example.adoptini.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeService {
    private UserRepository userRepository;
    private AnimalRepository animalRepository;
    private LikeRepository likeRepository;

    public Like createLike(ObjectId animalId, ObjectId userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No data!"));
        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new RuntimeException("No data!"));
        Like like = new Like();
        like.setAnimal(animal);
        like.setUser(user);
        return likeRepository.save(like);
    }

}
