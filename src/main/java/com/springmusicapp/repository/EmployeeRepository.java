package com.springmusicapp.repository;

import com.springmusicapp.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface EmployeeRepository<T extends Employee> extends UserRepository<T> {

    List<T> findByHireDate(LocalDate hireDate);

    @Query("SELECT e FROM #{#entityName} e WHERE e.hireDate BETWEEN :startDate AND :endDate")
    List<T> findByHireDateBetween(@Param("startDate") LocalDate hireDateStart, @Param("endDate") LocalDate hireDateEnd);

    List<T> findBySalary(Double salary);

    @Query("SELECT e FROM #{#entityName} e WHERE e.salary > :salaryGreater ")
    List<T> findBySalaryGreaterThan(@Param("salaryGreater")Double salary);

    @Query("SELECT e FROM #{#entityName} e WHERE e.salary < :salaryLess ")
    List<T> findBySalaryLessThan(@Param("salaryLess") Double salary);

    @Query("SELECT e FROM #{#entityName} e WHERE e.salary BETWEEN :salaryStart AND :salaryEnd")
    List<T> findBySalaryBetween(@Param("salaryStart") Double salaryStart, @Param("salaryEnd") Double salaryEnd);
}
