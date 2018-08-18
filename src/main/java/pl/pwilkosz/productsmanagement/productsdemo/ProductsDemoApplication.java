package pl.pwilkosz.productsmanagement.productsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ProductsDemoApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ProductsDemoApplication.class);
		logger.info("Starting container");

		SpringApplication.run(ProductsDemoApplication.class, args);

	}
}
