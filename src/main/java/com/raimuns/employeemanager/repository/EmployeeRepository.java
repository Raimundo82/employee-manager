package com.raimuns.employeemanager.repository;

import com.raimuns.employeemanager.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT employee FROM Employee employee WHERE employee.email = ?1")
    public Optional<Employee> findByEmail(String email);

}
