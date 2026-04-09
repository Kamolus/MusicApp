package com.springmusicapp.domain.label.service;

import com.springmusicapp.core.exception.BusinessLogicException;
import com.springmusicapp.domain.label.model.Employee;
import com.springmusicapp.domain.label.repository.EmployeeRepository;
import com.springmusicapp.domain.user.service.AbstractUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public abstract class AbstractEmployeeService<T extends Employee> extends AbstractUserService<T> {

    protected final EmployeeRepository<T> employeeRepository;

    public AbstractEmployeeService(EmployeeRepository<T> employeeRepository) {
        super(employeeRepository);
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void updateSalary(String employeeId, double newSalary) {
        T employee = getByIdOrThrow(employeeId);

        try {
            employee.setSalary(newSalary);
        } catch (IllegalArgumentException e) {
            throw new BusinessLogicException(e.getMessage(), "ERR_INVALID_SALARY");
        }

        employeeRepository.save(employee);
    }
}
