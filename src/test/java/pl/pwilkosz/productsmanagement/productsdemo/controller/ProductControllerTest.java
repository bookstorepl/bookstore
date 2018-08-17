package pl.pwilkosz.productsmanagement.productsdemo.controller;

import autofixture.publicinterface.Any;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.pwilkosz.productsmanagement.productsdemo.dao.ProductArchiveDao;
import pl.pwilkosz.productsmanagement.productsdemo.dao.ProductDao;
import pl.pwilkosz.productsmanagement.productsdemo.exceptions.ResourceNotFoundException;
import pl.pwilkosz.productsmanagement.productsdemo.model.Product;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    private final String PRODUCTS_URL = "/api/products";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductDao productDao;

    @MockBean
    private ProductArchiveDao productArchiveDao;

    @Test
    public void getAllProducts() throws Exception {

        //GIVEN
        long productId = Any.intValue();
        String description = Any.string();
        long productType = Any.intValue();
        Product flour = new Product();
        flour.setProductId(productId);
        flour.setDescription(description);
        flour.setProductTypeId(productType);

        List<Product> products = singletonList(flour);

        given(productDao.findAll()).willReturn(products);

        //WHEN
        mvc.perform(get(PRODUCTS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productId", is(flour.getProductId().intValue())))
                .andExpect(jsonPath("$[0].description", is(flour.getDescription())))
                .andExpect(jsonPath("$[0].productTypeId", is(flour.getProductTypeId().intValue())));

        //THEN
        //Done in previous step
    }

    @Test
    public void getProductById() throws Exception {

        //GIVEN
        long productId = Any.intValue();
        String description = Any.string();
        long productType = Any.intValue();
        Product flour = new Product();
        flour.setProductId(productId);
        flour.setDescription(description);
        flour.setProductTypeId(productType);

        given(productDao.findById(productId)).willReturn(Optional.of(flour));

        //WHEN
        mvc.perform(get(PRODUCTS_URL + "/" + productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(flour.getProductId().intValue())))
                .andExpect(jsonPath("$.description", is(flour.getDescription())))
                .andExpect(jsonPath("$.productTypeId", is(flour.getProductTypeId().intValue())));

        //THEN
        //Done in previous step
    }

    @Test
    public void getProductByIdWhenProductDoNotExistInDataBase() throws Exception {

        //GIVEN
        long productId = Any.intValue();
        given(productDao.findById(productId)).willThrow(new ResourceNotFoundException("Product", "id", productId));

        //WHEN
        mvc.perform(get(PRODUCTS_URL + "/" + productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //THEN
        //Done in previous step
    }

    @Test
    public void createProduct() throws Exception {

        //GIVEN
        long productId = Any.intValue();
        String description = Any.string();
        long productType = Any.intValue();
        Product flour = new Product();
        flour.setProductId(productId);
        flour.setDescription(description);
        flour.setProductTypeId(productType);

        //WHEN
        mvc.perform(post(PRODUCTS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(flour)))
                .andExpect(status().isOk());

        //THEN
    }

    @Test
    public void updateProduct() throws Exception {
        //GIVEN
        long productId = Any.intValue();
        String description = Any.string();
        long productType = Any.intValue();
        Product flour = new Product();
        flour.setProductId(productId);
        flour.setDescription(description);
        flour.setProductTypeId(productType);

        long productId2 = Any.intValue();
        String description2 = Any.string();
        long productType2 = Any.intValue();
        Product newFlour = new Product();
        newFlour.setProductId(productId2);
        newFlour.setDescription(description2);
        newFlour.setProductTypeId(productType2);

        given(productDao.findById(productId)).willReturn(Optional.of(flour));

        //WHEN
        mvc.perform(put(PRODUCTS_URL + "/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newFlour)))
                .andExpect(status().isOk());

        //THEN
    }

    @Test
    public void updateProductWhenProductDoNotExistInDataBase() throws Exception {
        //GIVEN
        long productId2 = Any.intValue();
        String description2 = Any.string();
        long productType2 = Any.intValue();
        Product newFlour = new Product();
        newFlour.setProductId(productId2);
        newFlour.setDescription(description2);
        newFlour.setProductTypeId(productType2);

        given(productDao.findById(productId2)).willThrow(new ResourceNotFoundException("Product", "id", productId2));

        //WHEN
        mvc.perform(put(PRODUCTS_URL + "/" + productId2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newFlour)))
                .andExpect(status().isNotFound());

        //THEN
    }

    @Test
    public void deletePruductById() throws Exception {

        //GIVEN
        long productId = Any.intValue();
        String description = Any.string();
        long productType = Any.intValue();
        Product flour = new Product();
        flour.setProductId(productId);
        flour.setDescription(description);
        flour.setProductTypeId(productType);

        given(productDao.findById(productId)).willReturn(Optional.of(flour));

        //WHEN
        mvc.perform(delete(PRODUCTS_URL + "/" + productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //THEN
    }

    @Test
    public void deletePruductByIdWhenProductDoNotExistInDataBase() throws Exception {

        //GIVEN
        long productId = Any.intValue();
        given(productDao.findById(productId)).willThrow(new ResourceNotFoundException("Product", "id", productId));

        //WHEN
        mvc.perform(delete(PRODUCTS_URL + "/" + productId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //THEN
    }

    @Test
    public void deleteAllPruducts() throws Exception {

        //GIVEN
        long productId = Any.intValue();
        String description = Any.string();
        long productType = Any.intValue();
        Product flour = new Product();
        flour.setProductId(productId);
        flour.setDescription(description);
        flour.setProductTypeId(productType);

        List<Product> products = singletonList(flour);

        given(productDao.findAll()).willReturn(products);

        //WHEN
        mvc.perform(delete(PRODUCTS_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //THEN
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}