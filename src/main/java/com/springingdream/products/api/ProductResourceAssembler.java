package com.springingdream.products.api;

import com.springingdream.products.api.controllers.ProductsController;
import com.springingdream.products.api.model.Product;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class ProductResourceAssembler implements ResourceAssembler<Product, Resource<Product>> {

    @Override
    public Resource<Product> toResource(Product p) {
        return new Resource<>(p,
                ControllerLinkBuilder.linkTo(methodOn(ProductsController.class).one(p.getId())).withSelfRel(),
                linkTo(methodOn(ProductsController.class).all()).withRel("products"));
    }
}
