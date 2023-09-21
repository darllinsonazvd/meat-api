package io.github.darllinsonazvd.sales.api.controller;

import io.github.darllinsonazvd.sales.domain.entity.Product;
import io.github.darllinsonazvd.sales.domain.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductsRepository productsRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product product) {
        return productsRepository.save(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Product product) {
        productsRepository
                .findById(id)
                .map(p -> {
                    product.setId(p.getId());
                    productsRepository.save(product);

                    return product;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id " + id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        productsRepository
                .findById(id)
                .map(p -> {
                    productsRepository.deleteById(id);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id " + id));
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Integer id) {
        return productsRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id " + id));
    }

    @GetMapping
    public List<Product> find(Product filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING
                );
        Example<Product> example = Example.of(filter, matcher);

        return productsRepository.findAll(example);
    }
}
