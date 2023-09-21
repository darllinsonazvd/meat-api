package io.github.darllinsonazvd.sales.api.controller;

import io.github.darllinsonazvd.sales.domain.entity.Customer;
import io.github.darllinsonazvd.sales.domain.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomersRepository customersRepository;

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Integer id) {
        return customersRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found for id " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer save(@RequestBody Customer customer) {
        return customersRepository.save(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        customersRepository
                .findById(id)
                .map(customer -> {
                    customersRepository.delete(customer);
                    return customer;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found for id " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Customer customer) {
        customersRepository.
                findById(id)
                .map(databaseCustomer -> {
                    customer.setId(databaseCustomer.getId());
                    customersRepository.save(customer);

                    return databaseCustomer;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found for id " + id));
    }

    @GetMapping
    public List<Customer> find(Customer filter) {
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING
                                    );
        Example<Customer> example = Example.of(filter, matcher);

        return customersRepository.findAll(example);
    }
}
