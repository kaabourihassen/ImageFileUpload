package com.example.adoptini.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.sql.Timestamp;
import java.util.List;

@Document(collection = "animals")
@Data@NoArgsConstructor@Getter@Setter
public class Animal{
    @Id
    private ObjectId id;
    @Field(value = "animalType")
    @Indexed
    private String animalType;
    @Field(value = "description")
    private String description;
    @Field(value = "created")
    private Timestamp created;
    @Field(value = "delivery")
    private boolean delivery;
    @Field(value = "gender")
    private  boolean gender;
    @Field(value = "animalAge")
    @Indexed
    private String animalAge;
    @Field(value = "location")
    private String location;
    @Field(value = "imageUrl")
    private String imageUrl;

    @Field(value = "user")
    @DBRef
    private User user;
    @Field(value = "likes")
    @DBRef
    private List<Like> likes;
    @Field(value = "images")
    @DBRef
    private List<Img> images;

    public Animal(String animalType, String description, Timestamp created, boolean delivery, boolean gender, String animalAge, String location, String imageUrl, User user, List<Like> likes) {
        this.animalType = animalType;
        this.description = description;
        this.created = created;
        this.delivery = delivery;
        this.gender = gender;
        this.animalAge = animalAge;
        this.location = location;
        this.imageUrl = imageUrl;
        this.user = user;
        this.likes = likes;
    }

    public ObjectId getId() {
        return id;
    }

    public String getAnimalType() {
        return animalType;
    }

    public String getAnimalAge() {
        return animalAge;
    }

    public boolean getGender(){
        return this.gender;
    }
    public boolean getDelivery(){
        return this.delivery;
    }




}
