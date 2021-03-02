package com.raimuns.employeemanager.service;

import com.raimuns.employeemanager.StringPatternValidator;
import com.raimuns.employeemanager.domain.Employee;
import com.raimuns.employeemanager.domain.EmployeeDto;
import com.raimuns.employeemanager.exceptions.AppException;
import com.raimuns.employeemanager.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.raimuns.employeemanager.exceptions.ErrorMessage.*;


@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final StringPatternValidator stringPatternValidator;

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        employeeDto.setEmployeeCode(UUID.randomUUID().toString());

        checkEmployeeConsistency(employeeDto, false);

        Employee employee = employeeRepository.save(new Employee(employeeDto));
        return new EmployeeDto(employee);
    }


    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeDto::new)
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(Long id) {
        return new EmployeeDto(checkIfEmployeeExists(id));
    }

    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee e = checkIfEmployeeExists(id);
        checkEmployeeConsistency(employeeDto, employeeDto.getEmail().equals(e.getEmail()));
        e.updateEmployee(employeeDto);
        return new EmployeeDto(e);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private void checkEmployeeConsistency(EmployeeDto employeeDto, boolean emailUpdate) {
        if (employeeDto.getName().isBlank() || employeeDto.getName().trim().isBlank()) {
            throw new AppException(EMPLOYEE_NAME_INVALID, employeeDto.getName());
        }

        if (!emailUpdate && employeeRepository.findByEmail(employeeDto.getEmail()).isPresent()) {
            throw new AppException(EMAIL_ALREADY_EXISTS, employeeDto.getEmail());
        }

        if (stringPatternValidator.isEmailValid(employeeDto.getEmail())) {
            if (!emailUpdate && employeeRepository.findByEmail(employeeDto.getEmail()).isPresent())
                throw new AppException(EMAIL_ALREADY_EXISTS, employeeDto.getEmail());
        } else {
            throw new AppException(EMAIL_FORMAT_INVALID, employeeDto.getEmail());
        }

        if (stringPatternValidator.isDobValid(employeeDto.getDob())) {
            if (Period.between(LocalDate.parse(employeeDto.getDob()), LocalDate.now()).getYears() < 18)
                throw new AppException(EMPLOYEE_IS_NOT_ADULT);
        } else {
            throw new AppException(DOB_FORMAT_INVALID, employeeDto.getDob());
        }

        if (Period.between(LocalDate.parse(employeeDto.getDob()), LocalDate.now()).getYears() < 18) {
            throw new AppException(EMPLOYEE_IS_NOT_ADULT);
        }

        if (employeeDto.getPhone().length() != 13 || !employeeDto.getPhone().startsWith("+3519")) {
            throw new AppException(PHONE_NUMBER_INVALID, employeeDto.getPhone());
        }
    }

    private Employee checkIfEmployeeExists(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new AppException(EMPLOYEE_NOT_FOUND_ID, id));
    }


}
