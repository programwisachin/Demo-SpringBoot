package com.example.program.controller;

import com.example.program.entity.Department;
import com.example.program.error.DepartmentNotFoundException;
import com.example.program.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {

        department = Department.builder()
                .departmentAddress("Ahilyanagar")
                .departmentCode("EnC-011")
                .departmentName("Electronics")
                .departmentID(1L)
                .build();

    }

    @Test
    void saveDepartment() throws Exception {

        Department inputDepartment = Department.builder()
                .departmentAddress("Ahilyanagar")
                .departmentCode("EnC-011")
                .departmentName("Electronics")
                .build();
        Mockito.when(departmentService.saveDepartment(inputDepartment))
                .thenReturn(department);

        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"departmentName\":\"Electronics\",\n" +
                        "    \"departmentAddress\":\"Ahilyanagar\",\n" +
                        "    \"departmentCode\":\"EnC-011\"\n" +
                        "}"))
                .andExpect(status().isOk());

    }

    @Test
    void fetchDepartmentById() throws DepartmentNotFoundException,Exception {

        Mockito.when(departmentService.fetchDepartmentById(1L)).thenReturn(department);
        mockMvc.perform(get("/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentName")
                        .value(department.getDepartmentName()));

    }
}