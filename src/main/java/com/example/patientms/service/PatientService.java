package com.example.patientms.service;

import com.example.patientms.model.Patient;
import com.example.patientms.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j // Lombok annotation for logging
@Service
public class PatientService {
    @Autowired
    private PatientRepository repository;

    public List<Patient> getAllPatients() {
        log.info("Fetching all patients");
        return repository.findAll();
    }

    public Patient getPatient(String id) {
        log.info("Fetching patient with ID: {}", id);
        return repository.findById(id).orElse(null);
    }

    public Patient savePatient(Patient patient) {
        log.info("Saving a new patient with ID: {}", patient.getId());
        return repository.save(patient);
    }

    public Patient updatePatient(String id, Patient patient) {
        patient.setId(id);
        log.info("Updating patient with ID: {}", id);
        return repository.save(patient);
    }

    public void deletePatient(String id) {
        log.info("Deleting patient with ID: {}", id);
        repository.deleteById(id);
    }
}
