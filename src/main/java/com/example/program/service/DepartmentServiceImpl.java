package com.example.program.service;

import com.example.program.entity.Department;
import com.example.program.error.DepartmentNotFoundException;
import com.example.program.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> fetchDepartmentList() {
        return departmentRepository.findAll();
    }

    @Override
    public Department fetchDepartmentById(Long departmentId) throws DepartmentNotFoundException {
        Optional<Department> department =departmentRepository.findById(departmentId);
        if (!department.isPresent()){
            throw new DepartmentNotFoundException("Department not available");
        }
        return department.get();
    }

    @Override
    public String deleteDepartmentById(Long departmentId) {
        departmentRepository.deleteById(departmentId);
        return "Deleted Successfully for ID - " + departmentId;
    }

    @Override
    public Department updateDepartment(Long departmentId, Department department) {
         Department depDB = departmentRepository.findById(departmentId).get();

         if(Objects.nonNull( department.getDepartmentAddress()) &&
                 !"".equalsIgnoreCase(department.getDepartmentAddress())){
                    depDB.setDepartmentAddress(department.getDepartmentAddress());
         }

        if(Objects.nonNull( department.getDepartmentCode()) &&
                !"".equalsIgnoreCase(department.getDepartmentCode())){
            depDB.setDepartmentCode(department.getDepartmentCode());
        }

        if(Objects.nonNull( department.getDepartmentName()) &&
                !"".equalsIgnoreCase(department.getDepartmentName())){
            depDB.setDepartmentName(department.getDepartmentName());
        }

        return departmentRepository.save(depDB);
    }

    @Override
    public Department fetchDepartmentByName(String name) {

        return departmentRepository.findByDepartmentName(name);
    }


}
