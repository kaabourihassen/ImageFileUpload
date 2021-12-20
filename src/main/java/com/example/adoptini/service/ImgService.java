package com.example.adoptini.service;

import com.example.adoptini.model.Img;
import com.example.adoptini.repository.ImgRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Service
public class ImgService {

    @Autowired
    private ImgRepository photoRepo;



    public Img getPhoto(ObjectId id) {
        return photoRepo.findById(id).get();
    }

    public Img save(Img photo) {
        return photoRepo.save(photo);
    }
}