package com.example.adoptini.controller;

import com.example.adoptini.exception.ResourceNotFoundException;
import com.example.adoptini.model.Animal;
import com.example.adoptini.model.Like;
import com.example.adoptini.repository.AnimalRepository;
import com.example.adoptini.service.AnimalService;
import com.example.adoptini.service.ImgService;
import com.example.adoptini.service.LikeService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("adoptini/animal")
@AllArgsConstructor
public class AnimalCRUDController {
    @Autowired
    AnimalService animalService;

    @Autowired
    LikeService likeService;

    @Autowired
    AnimalRepository animalRepository;
    @Autowired
    ImgService imgService;


    @GetMapping("/list")
    public List<Animal> animalList(){
        return animalService.animalsList();
    }


    @GetMapping("/details/{animalId}")
    public Animal animalDetails(@PathVariable ObjectId animalId){
        return animalService.animalDetails(animalId);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createAnimal(@RequestBody Animal animal, @RequestParam("imageArray") MultipartFile[] multipartFileLst) throws IOException {
        return animalService.createAnimal(animal,multipartFileLst);
    }

    @PutMapping(value = "/update/{animalId}")
    public Animal updateAnimal(@PathVariable ObjectId animalId ,@RequestBody Animal animal) throws ResourceNotFoundException {
        return animalService.updateAnimal(animalId,animal);
    }


    @DeleteMapping("/delete/{animalId}")
    public void deleteAnimal(@PathVariable ObjectId animalId){
        animalRepository.deleteById(animalId);
    }


    @PostMapping("like/{animalId}/{userId}")
    public Like createLike(@PathVariable ObjectId animalId, @PathVariable ObjectId userId){
        return likeService.createLike(animalId, userId);
    }
}
