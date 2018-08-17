package pl.pwilkosz.productsmanagement.productsdemo.dao;

import autofixture.publicinterface.Any;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pwilkosz.productsmanagement.productsdemo.model.Product;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductDao productDao;

    @Test
    public void whenFindByIdThenReturnProduct() {
        //GIVEN
        long productId = Any.intValue();
        String description = Any.string();
        long productType = Any.intValue();
        Product flour = new Product();
        flour.setProductId(productId);
        flour.setDescription(description);
        flour.setProductTypeId(productType);
        entityManager.persist(flour);
        entityManager.flush();

        //WHEN
        Optional<Product> found = productDao.findById(productId);

        //THEN
        assertThat(flour.getProductId()).isEqualTo(found.get().getProductId());
        assertThat(flour.getDescription()).isEqualTo(found.get().getDescription());
        assertThat(flour.getProductTypeId()).isEqualTo(found.get().getProductTypeId());
    }
}