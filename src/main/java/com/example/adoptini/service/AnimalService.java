package com.example.adoptini.service;

import com.example.adoptini.exception.FileStorageException;
import com.example.adoptini.exception.ResourceNotFoundException;
import com.example.adoptini.model.Animal;
import com.example.adoptini.model.Img;
import com.example.adoptini.property.FileStorageProperties;
import com.example.adoptini.repository.AnimalRepository;
import com.example.adoptini.repository.ImgRepository;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@AllArgsConstructor
    public class AnimalService implements UserDetailsService {
    private AnimalRepository animalRepository;
    private ImgRepository imgRepository;
    private final String ANIMAL_NOT_FOUND = "cannot find user with type %s";
    private ImgService imgService;
    private final Path fileStorageLocation;


    public Animal findByAnimalId(ObjectId animalId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new RuntimeException("No data!"));
        return animal;
    }

    @Transactional
    public Animal updateAnimal(ObjectId animalId,Animal animal)  throws ResourceNotFoundException {
        Animal animal1;
        animal1= animalRepository.findById(animalId)  .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + animalId));
        animal1.setAnimalAge(animal.getAnimalAge());
        animal1.setDescription(animal.getDescription());
        animal1.setGender(animal.getGender());
        animal1.setDelivery(animal.getDelivery());
        animal1.setAnimalType(animal.getAnimalType());
        animal1.setLocation(animal.getLocation());
        animal1.setImages(animal.getImages());

        return animalRepository.save(animal1);
    }

    @Autowired
    public AnimalService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    @Transactional
    public ResponseEntity createAnimal(Animal animal, MultipartFile[] multipartFileLst) throws IOException {
        List<Img> photos = null;
        try {
            for(MultipartFile multipartFile : multipartFileLst){
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                Img photo = new Img();
                photo.setTitle(multipartFile.getOriginalFilename());
                photo.setFile(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
                photo = imgRepository.insert(photo);
                photos.add(photo);
            }
            animal.setImages(photos);
            animalRepository.save(animal);
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
