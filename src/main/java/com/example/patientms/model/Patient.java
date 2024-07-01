package com.example.patientms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor // Generates a no-argument constructor required by Spring Data
@AllArgsConstructor // Generates a constructor with all arguments
@Document
public class Patient {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String contactNumber;
    private String email;
    private String gender;
}

