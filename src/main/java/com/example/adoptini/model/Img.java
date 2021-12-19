package com.example.adoptini.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "images")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Img {
    @Id
    private ObjectId id;
    @Field(value = "title")
    private String title;
    @Field(value = "imageArray")
    private Binary file;
    @DBRef
    private User user;

}
