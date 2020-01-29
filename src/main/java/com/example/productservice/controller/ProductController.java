package com.example.productservice.controller;

import com.example.productservice.data.ProductData;
import com.example.productservice.data.Response;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    //1
    @GetMapping("view")
    public List<ProductData> viewAllProduct() {

        return productService.viewAll();
    }

    //2
    @GetMapping("view/{id}")
    public ProductData viewSingleProduct(@PathVariable("id") long productId) {

        return productService.viewSingle(productId);

    }

    //3
    @DeleteMapping("delete/{id}")
    public Response deleteProduct(@PathVariable("id") long productId) {

        productService.delete(productId);

        Response response = new Response();
        response.setMessage("Product deleted");
        return response;
    }

    //4
    @PostMapping("update/{id}")
    public Response updateProduct(@PathVariable("id") long productId, @RequestBody ProductData productData) {

        productService.update(productId, productData);

        Response response = new Response();
        response.setMessage("Product updated");
        return response;

    }

    //5
    @PostMapping("add")
    public Response addProduct(@RequestBody ProductData productData) {

        productService.save(productData);

        Response response = new Response();
        response.setMessage("Product added");
        return response;
    }

}
