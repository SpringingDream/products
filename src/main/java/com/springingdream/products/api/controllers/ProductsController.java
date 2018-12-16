package com.springingdream.products.api.controllers;

import com.springingdream.products.api.model.Product;
import com.springingdream.products.api.ProductNotFoundException;
import com.springingdream.products.api.ProductRepository;
import com.springingdream.products.api.ProductResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "/products")
public class ProductsController {
    private final ProductRepository repository;

    private final ProductResourceAssembler assembler;

    @Autowired
    public ProductsController(ProductRepository repository, ProductResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resources<Resource<Product>> all() {
        List<Resource<Product>> list = StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(list, linkTo(methodOn(ProductsController.class).all()).withSelfRel());
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    Resource<Product> insert(@RequestBody Product p) {
        return assembler.toResource(repository.save(p));
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public Resource<Product> one(@PathVariable Integer id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return assembler.toResource(product);
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    Resource<Product> replace(@RequestBody Product product, @PathVariable Integer id) {
        return assembler.toResource(repository.findById(id)
                .map(p -> {
                    p.setTitle(product.getTitle());
                    p.setDescription(product.getDescription());
                    p.setCost(product.getCost());
                    return repository.save(p);
                })
                .orElseGet(() -> {
                    product.setId(id);
                    return repository.save(product);
                }));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
