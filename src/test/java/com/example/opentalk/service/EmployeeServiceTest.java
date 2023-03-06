package com.example.opentalk.service;


import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import com.example.opentalk.dto.EmployeeDTO;
import static org.mockito.Mockito.verify;
import com.example.opentalk.entity.Employee;
import com.example.opentalk.mapper.EmployeeMapper;
import com.example.opentalk.repository.CompanyBranchRepository;
import com.example.opentalk.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;
//            = Mockito.mock(EmployeeRepository.class);



    @Test
    public void whenGetAll_shouldReturnList() {
        List<Employee> expectEmployees = Arrays.asList(
                new Employee(1L, "KhanhTran", "ggggg", "khanhtran@gmail.com", "USER", false, "1000", null, null, null),
                new Employee(2L, "KhanhTran", "ggggg", "khanhtran@gmail.com", "USER", false, "1000", null, null, null)
        );

        Mockito.when(employeeRepository.findAll()).thenReturn(expectEmployees);

        List<EmployeeDTO> actualEmployees = employeeService.findAll();

        assertThat(expectEmployees.size()).isEqualTo(actualEmployees.size());

    }
}



