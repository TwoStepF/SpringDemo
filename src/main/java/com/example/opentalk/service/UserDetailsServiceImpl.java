package com.example.opentalk.service;

import com.example.opentalk.dto.GetEmployeeDTO;
import com.example.opentalk.entity.Employee;
import com.example.opentalk.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        GetEmployeeDTO employee = employeeRepository.findEmployeeNotDecript(username);
        return new org.springframework.security.core.userdetails.User(employee.getName(),
                employee.getHashPassword(),
                employee.getEnable(), true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority(employee.getRole())));
    }

}
