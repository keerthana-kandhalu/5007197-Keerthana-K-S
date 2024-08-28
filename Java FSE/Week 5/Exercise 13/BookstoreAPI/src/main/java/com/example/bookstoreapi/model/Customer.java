package com.example.bookstoreapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    private String address;

    // Getters and Setters
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @Setter
    @Getter
    @NotNull
    @Email
    private String email;

    @Setter
    @Getter
    @Size(min = 10, max = 15)
    private String phoneNumber;
}