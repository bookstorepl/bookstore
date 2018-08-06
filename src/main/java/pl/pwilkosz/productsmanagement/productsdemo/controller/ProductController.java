package pl.pwilkosz.productsmanagement.productsdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pwilkosz.productsmanagement.productsdemo.dao.ProductArchiveDao;
import pl.pwilkosz.productsmanagement.productsdemo.dao.ProductDao;
import pl.pwilkosz.productsmanagement.productsdemo.exceptions.ResourceNotFoundException;
import pl.pwilkosz.productsmanagement.productsdemo.model.Product;
import pl.pwilkosz.productsmanagement.productsdemo.model.ProductArchive;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductDao productDao;
    @Autowired
    ProductArchiveDao productArchiveDao;

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
        Product productDeleted = productDao.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));
        productDao.delete(productDeleted);
        productDao.flush();
        archiveProduct(productDeleted);

    }

    @DeleteMapping("/products")
    public void deleteAllPruducts(){
        List<Product> products = productDao.findAll();
        for (Product productDeleted : products) {
            productDao.delete(productDeleted);
            productDao.flush();
            archiveProduct(productDeleted);
        }
    }

    private void archiveProduct(Product productDeleted){
        ProductArchive productArchive = new ProductArchive();
        productArchive.setProductId(productDeleted.getProductId());
        productArchive.setDescription(productDeleted.getDescription());
        productArchive.setProductTypeId(productDeleted.getProductTypeId());
        productArchiveDao.save(productArchive);
    }
}
