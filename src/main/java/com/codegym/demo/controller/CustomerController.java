package com.codegym.demo.controller;

import com.codegym.demo.model.Customer;
import com.codegym.demo.service.customer.ICustomerService;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    //    @GetMapping("/create-customer")
//    public ModelAndView showCreateForm(){
//        ModelAndView modelAndView = new ModelAndView("/customer/create");
//        modelAndView.addObject("customer",new Customer());
//        return modelAndView;
//    }
//    @PostMapping("/create-customer")
//    public ModelAndView saveCustomer(@ModelAttribute("customer")Customer customer){
//        customerService.save(customer);
//        ModelAndView modelAndView = new ModelAndView("/customer/create");
//        modelAndView.addObject("customer",new Customer());
//        modelAndView.addObject("message","New customer created successfully!");
//        return modelAndView;
//    }
//    @GetMapping("/customers")
//    public ModelAndView showListCustomer(){
//        ModelAndView modelAndView = new ModelAndView("/customer/list");
//        modelAndView.addObject("customers",customerService.findAll());
//        return modelAndView;
//    }
//    @GetMapping("/edit-customer/{id}")
//    public ModelAndView showEditForm(@PathVariable Long id){
//        Optional<Customer> customerOptional = customerService.findById(id);
//        if (customerOptional.isPresent()){
//            ModelAndView modelAndView = new ModelAndView("/customer/edit");
//            modelAndView.addObject("customer",customerOptional.get());
//            return modelAndView;
//        }else {
//            return new ModelAndView("/erorr.404");
//        }
//
//    }


    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customerService.save(customer);
        return new ResponseEntity<>(customerService.findById(customer.getId()).get(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> editCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> customerOptional = customerService.findById(id);
        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customer.setId(customerOptional.get().getId());
        customerService.save(customer);
        return new ResponseEntity<>(customerService.findById(customer.getId()).get(), HttpStatus.OK);
    }

    @GetMapping("/search/{value}")
    public ResponseEntity<Iterable<Customer>> findByName(@PathVariable String value) {
        return new ResponseEntity<>(customerService.findAllByFirstNameContaining(  value ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (!customer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.remove(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Customer>> findAll(@PageableDefault(size = 3, direction = Sort.Direction.DESC, sort = "id") Pageable pageable) {
        Page<Customer> customers =  customerService.findAll(pageable);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<Customer>> findAll() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }
}
//    @PostMapping("/edit-customer")
//    public ModelAndView updateCustomer(@ModelAttribute("customer")Customer customer){
//        customerService.save(customer);
//        ModelAndView modelAndView = new ModelAndView("/customer/edit");
//        modelAndView.addObject("customer",customer);
//        modelAndView.addObject("message","Customer update successfully!");
//        return modelAndView;
//
//    }
//    @GetMapping("/delete-customer/{id}")
//    public ModelAndView showDeleteForm(@PathVariable Long id){
//        Optional<Customer> customerOptional = customerService.findById(id);
//        if (customerOptional.isPresent()){
//            ModelAndView modelAndView = new ModelAndView("/customer/delete");
//            modelAndView.addObject("customer",customerOptional.get());
//            return modelAndView;
//        }else {
//            return new ModelAndView("/erorr.404");
//        }
//
//    }
//    @PostMapping("/delete-customer")
//    public String deleteCustomer(@ModelAttribute("customer") Customer customer) {
//        customerService.remove(customer.getId());
//        return "redirect:/customers";
//    }
//}
