package pl.pwilkosz.productsmanagement.productsdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwilkosz.productsmanagement.productsdemo.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
}
