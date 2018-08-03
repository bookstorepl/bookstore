package pl.pwilkosz.productsmanagement.productsdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pwilkosz.productsmanagement.productsdemo.dao.ProductDao;
import pl.pwilkosz.productsmanagement.productsdemo.exceptions.ResourceNotFoundException;
import pl.pwilkosz.productsmanagement.productsdemo.model.Product;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductDao productDao;

    // Select all products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable(value="id") Long  productId){
        return productDao.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    //HTTP POST
    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product){
        return productDao.save(product);
    }

    //HTTP PUT
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable(value="id") Long productId,
                                 @Valid @RequestBody Product product){
        Product productUpdated = productDao.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));

        productUpdated.setDescription(product.getDescription());
        productUpdated.setProductTypeId(product.getProductTypeId());

       return productDao.save(productUpdated);
    }

    //HTTP DELETE
    @DeleteMapping("/products/{id}")
    public void deletePruductById(@PathVariable(value="id") Long productId){
        productDao.deleteById(productId);
    }

    @DeleteMapping("/products")
    public void deleteAllPruducts(){
        productDao.deleteAll();
    }
}
