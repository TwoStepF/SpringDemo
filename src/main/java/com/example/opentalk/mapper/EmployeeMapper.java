package com.example.opentalk.mapper;

import com.example.opentalk.dto.CreateEmployeeRequest;
import com.example.opentalk.dto.EmployeeDTO;
import com.example.opentalk.entity.Company_branch;
import com.example.opentalk.entity.Employee;
import com.example.opentalk.repository.CompanyBranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeMapper {
    private final PasswordEncoder passwordEncoder;
    private final CompanyBranchRepository companyBranchRepository;


    public Employee MapDtoToEmployee(CreateEmployeeRequest createEmployeeRequest){
        String Hash_password = passwordEncoder.encode(createEmployeeRequest.getPassword());
        Company_branch company_branch = companyBranchRepository.findCompany_branchById(createEmployeeRequest.getCompany_branch());
        return Employee.builder()
                .name(createEmployeeRequest.getName())
                .email(createEmployeeRequest.getEmail())
                .hashPassword(Hash_password)
                .company_branch(company_branch)
                .build();
    }
    public void delete (){

    }
}
