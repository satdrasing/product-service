package com.example.productservice.service;

import com.example.productservice.dao.ProductDao;
import com.example.productservice.data.ProductData;
import com.example.productservice.entity.ProductEntity;
import com.example.productservice.exception.ProductNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    private ProductDao productDao;


    public List<ProductData> viewAll() {
        List<ProductEntity> productEntityList = productDao.findAll();

        return productEntityList.stream()
                .map(product -> new ProductData(product.getProductId(), product.getProductName(), product.getProductDescription(), product.getProductPrice()))
                .collect(Collectors.toList());
    }

    public ProductData viewSingle(long productId) {

        Optional<ProductEntity> productEntity = productDao.findById(productId);

        ProductData productData = new ProductData();
        BeanUtils.copyProperties(productEntity.orElseThrow(ProductNotFoundException::new), productData);

        return productData;
    }

    public boolean delete(long productId ) {

        Optional<ProductEntity> productEntity = productDao.findById(productId);
        productEntity.orElseThrow(ProductNotFoundException::new);

        productDao.delete(productEntity.get());

        return true;
    }

    public boolean update(long productId, ProductData ProductData) {

        ProductData.setProductId(productId);
        Optional<ProductEntity> productEntity = productDao.findById(productId);

        productEntity.orElseThrow(ProductNotFoundException::new);

        BeanUtils.copyProperties(ProductData,productEntity.get());
        productDao.save(productEntity.get());

        return true;
    }

    public boolean save(ProductData productData) {

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productData, productEntity);

        productDao.save(productEntity);

        return true;
    }
}
