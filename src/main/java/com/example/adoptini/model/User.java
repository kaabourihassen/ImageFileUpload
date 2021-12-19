package com.example.adoptini.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private ObjectId id;
    @Field(value = "firstName")
    private String firstName;
    @Field(value = "lastName")
    private String lastName;
    @Field(value = "email")
    @Indexed(unique = true)
    private String email;
    @Field(value = "password")
    private String password;
    @Field(value = "role")
    @JsonProperty("role")
    private UserRole role;
    @Field(value = "locked")
    private Boolean locked;
    @Field(value = "enabled")
    private Boolean enabled;

    public User(String firstName, String lastName, String email, String password, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
        this.enabled = enabled;
    }

}
