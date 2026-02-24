package com.shoppingmall.repository;

import com.shoppingmall.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByUserId(int userId);
}
