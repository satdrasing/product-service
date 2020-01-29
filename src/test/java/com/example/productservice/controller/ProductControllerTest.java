package com.example.productservice.controller;


import com.example.productservice.data.ProductData;
import com.example.productservice.data.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Sql({"/data.sql"})
    @Test
    public void testAllProducts_view_individual() {
        ProductData responseEntity = this.restTemplate.getForObject("http://localhost:" + port + "/view/1", ProductData.class);
        assertEquals(1, responseEntity.getProductId());
        assertEquals("test product 1", responseEntity.getProductName());
    }

    @Test
    public void testAllEmployees_with_exception() {
        Response responseEntity = this.restTemplate.getForObject("http://localhost:" + port + "/view/10", Response.class);
        assertEquals("product not found", responseEntity.getMessage());
    }

    @Sql({"/data.sql"})
    @Test
    public void testAllProducts_view_all() {
        List productData = this.restTemplate.getForObject("http://localhost:" + port + "/view", List.class);
        assertEquals("{productId=2, productName=test product 2, productDescription=test description 2, productPrice=22.0}",
                productData.get(1).toString());
    }


    @Test
    public void testAddProduct() {

        ProductData product = new ProductData();
        product.setProductName("test product name");
        product.setProductDescription("test product description");
        product.setProductPrice(89.0);

        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/add", product, String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("{\"message\":\"Product added\"}", responseEntity.getBody());

    }
}
