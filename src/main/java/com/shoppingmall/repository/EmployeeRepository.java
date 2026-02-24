package com.shoppingmall.repository;

import com.shoppingmall.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByUserId(int userId);
}
