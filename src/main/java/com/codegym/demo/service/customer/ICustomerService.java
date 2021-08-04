package com.codegym.demo.service.customer;

import com.codegym.demo.model.Customer;
import com.codegym.demo.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IGeneralService<Customer> {
    Page<Customer> findAll(Pageable pageable);
    Iterable<Customer> findAllByFirstNameContaining(String name);
}
