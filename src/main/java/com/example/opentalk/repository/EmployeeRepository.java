package com.example.opentalk.repository;
import com.example.opentalk.dto.GetEmployeeDTO;
import com.example.opentalk.entity.Employee;
import com.example.opentalk.model.EmployeeInterface;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query(value = "select e from Employee as e")
    List<Employee> findAll();

    Employee findEmployeeByName(String name);

    @Query(value = "select new com.example.opentalk.dto.GetEmployeeDTO(name, role, hashPassword, enable) from Employee where name = :name")
    GetEmployeeDTO findEmployeeNotDecript(String name);

    Employee findEmployeeByEmail(String name);
    @Query(value = "select * from employee",
            nativeQuery = true)
//    @Cacheable("employee")
    Page<Employee> filterEmployee(Pageable pag);

    @Query(value = "select * from employee", nativeQuery = true)
    List<EmployeeInterface> findEmployeeAndMapToITF();

    @Query(value = "select e from Employee as e")
    List<Employee> findAndSoft(Sort sort);

    @Query(value = "select e from Employee as e where e.id = :id")
    Employee findByEmployeeId(Long id);
}
