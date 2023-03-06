package com.example.opentalk.service;

import com.example.opentalk.dto.EmployeeDTO;
import com.example.opentalk.entity.Company_branch;
import com.example.opentalk.entity.Employee;
//import com.example.opentalk.mapper.EmployeeMapstruct;
import com.example.opentalk.model.EmployeeInterface;
import com.example.opentalk.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
//    private final EmployeeMapstruct employeeMapstruct;

    private String generatePassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++)
            sb.append(chars.charAt(random.nextInt(chars.length())));
        return sb.toString();
    }

    public EmployeeDTO MapDataEmployeetoDTO(Employee employee){
        return EmployeeDTO.builder()
                .name(employee.getName())
                .role(employee.getRole())
                .email(employee.getEmail())
                .id(employee.getId())
//                .company(employee.getCompany_branch().getBranch_Name())
                .enable(employee.getEnable()).build();
    }

    public List<EmployeeDTO> filterEmployee(boolean enable, String company, String name, int page, int limit) {
        Pageable paging = PageRequest.of(page, limit);
        Page<Employee> employees = employeeRepository.filterEmployee(paging);
        return employees.stream()
                .map(this::MapDataEmployeetoDTO)
                .collect(Collectors.toList());
    }


    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll().stream()
                .map(this::MapDataEmployeetoDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> findAndSoft() {
        Sort sort = Sort.by("name").descending();
        return employeeRepository.findAndSoft(sort).stream()
                .map(this::MapDataEmployeetoDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeInterface> find(){
        return employeeRepository.findEmployeeAndMapToITF();
    }
}
