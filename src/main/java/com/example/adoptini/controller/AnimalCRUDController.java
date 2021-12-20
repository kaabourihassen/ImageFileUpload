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


    @PostMapping()
    public ResponseEntity createAnimal(@RequestPart("animal") Animal animal, @RequestParam("file") MultipartFile[] multipartFileLst) throws IOException {
//        System.out.println("*********************************************");
//        System.out.println(animal.getDescription());
//        System.out.println("*********************************************");
        return animalService.createAnimal(animal,multipartFileLst);
    }

    @PutMapping(value = "/update/{animalId}")
    public ResponseEntity updateAnimal(@PathVariable ObjectId animalId , @RequestPart("animal") Animal animal, @RequestParam("file") MultipartFile[] multipartFileLst) throws ResourceNotFoundException {
        return animalService.updateAnimal(animalId,animal,multipartFileLst);
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
