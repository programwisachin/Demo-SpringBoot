package com.example.program.service;

import com.example.program.entity.Department;
import com.example.program.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockitoBean
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department department =
                Department.builder()
                        .departmentID(1L)
                        .departmentName("IT")
                        .departmentCode("IT-06")
                        .departmentAddress("Pune")
                        .build();

        List<Department> departmentList =
                Collections.singletonList(
                        Department.builder()
                                .departmentID(1L)
                                .departmentName("IT")
                                .departmentCode("IT-06")
                                .departmentAddress("Pune")
                                .build()
                );
        Department updatedDepartment =
                Department.builder()
                        .departmentID(1L)
                        .departmentName("IT")
                        .departmentCode("IT-06")
                        .departmentAddress("Mumbai")
                        .build();


        Mockito.when(departmentRepository.findByDepartmentName("IT")).thenReturn(department);
        Mockito.when(departmentRepository.findAll()).thenReturn(departmentList);
        Mockito.when(departmentRepository.findById(department.getDepartmentID())).thenReturn(Optional.of(department));
        Mockito.when(departmentRepository.save(department)).thenReturn(updatedDepartment);
    }

    @Test
    @DisplayName("Fetch by name in a Department")
    public void whenValidDepartmentName_thenDepartmentShouldFound() {
        String departmentName = "IT";
        Department found = departmentService.fetchDepartmentByName(departmentName);
        assertEquals(departmentName, found.getDepartmentName());

    }

    @Test
    @DisplayName("Fetch all in a Department")
    public void whenValidDepartment_thenDepartmentShouldFound() {
        List<Department> department =
                Collections.singletonList(Department.builder()
                        .departmentID(1L)
                        .departmentName("IT")
                        .departmentCode("IT-06")
                        .departmentAddress("Pune")
                        .build());

        List<Department> found = departmentService.fetchDepartmentList();
        assertEquals(department, found);
    }

    @Test
    @DisplayName("Update value in a Department")
    public void whenValidUpdation_thenDepartmentShouldReturn() {
        Department editedDepartment =
                Department.builder()
                        .departmentID(1L)
                        .departmentName("IT")
                        .departmentCode("IT-06")
                        .departmentAddress("Mumbai")
                        .build();

        Department found = departmentService.updateDepartment(editedDepartment.getDepartmentID(), editedDepartment);
        assertEquals(editedDepartment, found);

    }

    @Test
    @DisplayName("Delete a Department")
    public void whenDeleteDepartment_thenDepartmentShouldDelete() {

        String deletion = "Deleted Successfully for ID - 1";
        Mockito.doNothing().when(departmentRepository).deleteById(1L);
        String found = departmentService.deleteDepartmentById(1L);
        assertEquals(deletion, found);

    }
}