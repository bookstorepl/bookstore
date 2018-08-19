package pl.pwilkosz.productsmanagement.productsdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pwilkosz.productsmanagement.productsdemo.dao.ProductArchiveDao;
import pl.pwilkosz.productsmanagement.productsdemo.exceptions.ResourceNotFoundException;
import pl.pwilkosz.productsmanagement.productsdemo.model.ProductArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@RestController
@RequestMapping("/api/archive")
public class ProductArchiveController {
    @Autowired
    private ProductArchiveDao productArchiveDao;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    // Select all products
    @GetMapping("/products")
    public List<ProductArchive> getAllProducts() {
        logger.debug("CALLING_API - products archive method GET ALL");
        return productArchiveDao.findAll();
    }

    @GetMapping("/products/{id}")
    public ProductArchive getProductById(@PathVariable(value="id") Long  productId){
        logger.debug("CALLING_API - products archive method GET");
        return productArchiveDao.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }
}
