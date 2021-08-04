package com.codegym.demo.repository;

import com.codegym.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer,Long> {
    Page<Customer> findAll(Pageable pageable);

    Iterable<Customer> findAllByFirstNameContaining(String name);
}
