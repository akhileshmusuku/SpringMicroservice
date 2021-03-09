package com.myretail.product.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myretail.product.dbentity.ProductEntity;

@Component
public class ProductDAOImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDAOImpl.class);

	@Autowired
	private ProductRepository productRepository;

	public void saveProduct(ProductEntity request) {
		productRepository.save(request);

		LOGGER.info("updated successfully {}", request.getId());
	}

	public Optional<ProductEntity> getProduct(long id) {
		return productRepository.findById(id);
	}
	
	
}
