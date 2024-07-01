package com.example.patientms.RestTemplate;

import com.example.patientms.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PatientServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080/patients";

    public Patient[] getAllPatients() {
        return restTemplate.getForObject(baseUrl, Patient[].class);
    }

    public Patient getPatient(String id) {
        return restTemplate.getForObject(baseUrl + "/" + id, Patient.class);
    }

    public Patient addPatient(Patient patient) {
        return restTemplate.postForObject(baseUrl, patient, Patient.class);
    }

    public void updatePatient(String id, Patient patient) {
        restTemplate.put(baseUrl + "/" + id, patient);
    }

    public void deletePatient(String id) {
        restTemplate.delete(baseUrl + "/" + id);
    }
}
