package com.example.adoptini.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = "likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Like{
    @Id
    private ObjectId likeId;
    @Indexed
    @Field(value = "user_id")
    @DBRef
    private User user;
    @Indexed
    @Field(value = "animal_id")
    @DBRef
    private Animal animal;

    public Like(User user, Animal animal) {
        this.user = user;
        this.animal = animal;
    }
}
