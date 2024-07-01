package com.example.patientms.controller;

import com.example.patientms.model.Patient;
import com.example.patientms.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

// JSON Path and Hamcrest Matchers
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPatients() throws Exception {
        List<Patient> patients = Arrays.asList(
                new Patient("1", "John", "Doe", "1980-01-01", "111-222-3333", "john@example.com", "Male"),
                new Patient("2", "Jane", "Doe", "1985-05-05", "444-555-6666", "jane@example.com", "Female")
        );

        given(patientService.getAllPatients()).willReturn(patients);

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    public void testGetPatientById() throws Exception {
        Patient patient = new Patient("1", "John", "Doe", "1980-01-01", "111-222-3333", "john@example.com", "Male");
        given(patientService.getPatient("1")).willReturn(patient);

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    public void testAddPatient() throws Exception {
        Patient newPatient = new Patient(null, "Alice", "Wonder", "1990-11-11", "777-888-9999", "alice@example.com", "Female");
        Patient savedPatient = new Patient("3", "Alice", "Wonder", "1990-11-11", "777-888-9999", "alice@example.com", "Female");
        given(patientService.savePatient(any(Patient.class))).willReturn(savedPatient);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("3")));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        Patient updatedPatient = new Patient("1", "Johnny", "Doe", "1980-01-01", "111-222-3333", "johnny@example.com", "Male");
        given(patientService.updatePatient(any(String.class), any(Patient.class))).willReturn(updatedPatient);

        mockMvc.perform(put("/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("johnny@example.com")));
    }

    @Test
    public void testDeletePatient() throws Exception {
        doNothing().when(patientService).deletePatient("1");

        mockMvc.perform(delete("/patients/1"))
                .andExpect(status().isOk());
    }
}
