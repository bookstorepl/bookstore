package pl.pwilkosz.productsmanagement.productsdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwilkosz.productsmanagement.productsdemo.dao.ProductArchiveDao;
import pl.pwilkosz.productsmanagement.productsdemo.dao.ProductDao;
import pl.pwilkosz.productsmanagement.productsdemo.exceptions.ResourceNotFoundException;
import pl.pwilkosz.productsmanagement.productsdemo.model.Product;
import pl.pwilkosz.productsmanagement.productsdemo.model.ProductArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductArchiveDao productArchiveDao;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    // Select all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        logger.debug("CALLING_API - products method GET ALL");
        return productDao.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable(value="id") Long  productId){
        logger.debug("CALLING_API - products method GET");
        return productDao.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    //HTTP POST
    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product){
        logger.debug("CALLING_API - products method POST");
        return productDao.save(product);
    }

    //HTTP PUT
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable(value="id") Long productId,
                                 @Valid @RequestBody Product product){
        logger.debug("CALLING_API - products method PUT");
        Product productUpdated = productDao.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));

        productUpdated.setDescription(product.getDescription());
        productUpdated.setProductTypeId(product.getProductTypeId());

       return productDao.save(productUpdated);
    }

    //HTTP DELETE
    @DeleteMapping("/products/{id}")
    public void deletePruductById(@PathVariable(value="id") Long productId){
        logger.debug("CALLING_API - products method DELETE");
        Product productDeleted = productDao.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));
        productDao.delete(productDeleted);
        productDao.flush();
        archiveProduct(productDeleted);

    }

    @DeleteMapping("/products")
    public void deleteAllPruducts(){
        logger.debug("CALLING_API - products method DELETE ALL");
        List<Product> products = productDao.findAll();
        for (Product productDeleted : products) {
            productDao.delete(productDeleted);
            productDao.flush();
            archiveProduct(productDeleted);
        }
    }

    private void archiveProduct(Product productDeleted){
        logger.debug(String.format("Archiving product with id {}"), productDeleted.getProductId());
        ProductArchive productArchive = new ProductArchive();
        productArchive.setProductId(productDeleted.getProductId());
        productArchive.setDescription(productDeleted.getDescription());
        productArchive.setProductTypeId(productDeleted.getProductTypeId());
        productArchiveDao.save(productArchive);
    }
}
