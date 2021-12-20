package com.example.adoptini.service;

import com.example.adoptini.exception.ResourceNotFoundException;
import com.example.adoptini.model.Animal;
import com.example.adoptini.model.Img;
import com.example.adoptini.repository.AnimalRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AnimalService{
    @Autowired
    private AnimalRepository animalRepository;
    private final String ANIMAL_NOT_FOUND = "cannot find user with type %s";
    @Autowired
    private ImgService imgService;
    private static  String UPLOAD_DIR =".\\src\\main\\resources\\static\\imagesuploads\\";



    public Animal findByAnimalId(ObjectId animalId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new RuntimeException("No data!"));
        return animal;
    }

    @Transactional
    public ResponseEntity updateAnimal(ObjectId animalId, Animal animal, MultipartFile[] multipartFileLst)  throws ResourceNotFoundException {
        List<Img> photos = new ArrayList<Img>();
        try {
            for(MultipartFile multipartFile : multipartFileLst){

                InputStream inputStream = multipartFile.getInputStream();
                OutputStream outputStream = new FileOutputStream(new File(UPLOAD_DIR + multipartFile.getOriginalFilename()));
                int read = 0;
                byte[] bytes = new byte[1024];

                while((read = inputStream.read(bytes)) != -1 ){
                    outputStream.write(bytes,0,read);
                }
                outputStream.flush();
                outputStream.close();

                Img photo = new Img();
                photo.setTitle(multipartFile.getOriginalFilename());
                photo.setFile(multipartFile.getBytes());
                imgService.save(photo);
                photos.add(photo);
            }
            Animal animal1;
            animal1= animalRepository.findById(animalId)  .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + animalId));
            animal1.setAnimalAge(animal.getAnimalAge());
            animal1.setDescription(animal.getDescription());
            animal1.setGender(animal.getGender());
            animal1.setDelivery(animal.getDelivery());
            animal1.setAnimalType(animal.getAnimalType());
            animal1.setLocation(animal.getLocation());
            animal1.setImages(photos);

            animalRepository.save(animal1);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (IOException e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


    }

    @Transactional
    public ResponseEntity createAnimal(Animal animal, MultipartFile[] multipartFileLst) throws IOException {
        List<Img> photos = new ArrayList<Img>();
        try {
            for(MultipartFile multipartFile : multipartFileLst){

                InputStream inputStream = multipartFile.getInputStream();
                OutputStream outputStream = new FileOutputStream(new File(UPLOAD_DIR + multipartFile.getOriginalFilename()));
                int read = 0;
                byte[] bytes = new byte[1024];

                while((read = inputStream.read(bytes)) != -1 ){
                    outputStream.write(bytes,0,read);
                }
                outputStream.flush();
                outputStream.close();

                Img photo = new Img();
                photo.setTitle(multipartFile.getOriginalFilename());
                photo.setFile(multipartFile.getBytes());
                imgService.save(photo);
                photos.add(photo);
            }
            Animal animal1 = new Animal();
            animal1.setAnimalAge(animal.getAnimalAge());
            animal1.setDescription(animal.getDescription());
            animal1.setGender(animal.getGender());
            animal1.setDelivery(animal.getDelivery());
            animal1.setAnimalType(animal.getAnimalType());
            animal1.setLocation(animal.getLocation());
            animal1.setImages(photos);
            animalRepository.save(animal1);
            return new ResponseEntity(HttpStatus.CREATED);
        }catch (IOException e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


    }

    public List<Animal> animalsList() {
        return animalRepository.findAll();
    }
    public Animal animalDetails(ObjectId animalId) {
        return animalRepository.findById(animalId).orElseThrow(
                () -> new UsernameNotFoundException(String.format(ANIMAL_NOT_FOUND, animalId))
        );
    }
}
