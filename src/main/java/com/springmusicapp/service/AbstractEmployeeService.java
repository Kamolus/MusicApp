package com.springmusicapp.service;

import com.springmusicapp.exception.BusinessLogicException;
import com.springmusicapp.model.Employee;
import com.springmusicapp.repository.EmployeeRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEmployeeService<T extends Employee> extends AbstractUserService<T> {

    protected final EmployeeRepository<T> employeeRepository;

    public AbstractEmployeeService(EmployeeRepository<T> employeeRepository) {
        super(employeeRepository);
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void updateSalary(Long employeeId, double newSalary) {
        T employee = getByIdOrThrow(employeeId);

        try {
            employee.setSalary(newSalary);
        } catch (IllegalArgumentException e) {
            throw new BusinessLogicException(e.getMessage(), "ERR_INVALID_SALARY");
        }

        employeeRepository.save(employee);

    }
}
