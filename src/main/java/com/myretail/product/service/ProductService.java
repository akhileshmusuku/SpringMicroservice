package com.myretail.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myretail.product.dbentity.ProductEntity;
import com.myretail.product.exception.InternalServerErrorException;
import com.myretail.product.exception.JsonProsessingException;
import com.myretail.product.exception.ResourceNotFoundException;
import com.myretail.product.model.Pricing;
import com.myretail.product.model.Product;
import com.myretail.product.repository.ProductDAOImpl;
import com.myretail.product.repository.ProductInfoAPIDao;

@Service
public class ProductService {

	@Autowired
	private ProductDAOImpl productDAO;

	@Autowired
	private ProductInfoAPIDao productAPIDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
	
	
	
	public Product updateProduct(Product request, long id) {
		
		Map<Long, Double> source = new HashMap<Long, Double>();
		
		Map<Long, Double> source1 = new HashMap<Long, Double>();
		
		source.put((long) 13860428, 30.0);
		source.put((long) 13860428, 45.0);
		
		source1.put((long) 13860428, 50.0);
		source1.put((long) 13860428, 68.0);
		
		double min1 =source.entrySet().stream()
		   .filter(key -> key.getKey() == id)
		   .min((x , y) -> Double.compare(x.getValue(), y.getValue())).get().getValue();
		LOGGER.info("min {}", min1);
		
		double min2 = source1.entrySet().stream()
		.filter(key -> key.getKey() == id)
		.min((x, y) -> Double.compare(x.getValue(), y.getValue()))
		.get().getValue();
		LOGGER.info("min 2{}", min2);
		
		if(id < min1 && id < min2) {
			throw new InternalServerErrorException("price value is less than min");
		}
		

		LOGGER.info("inside update service {}", request);

		Optional<ProductEntity> dbProduct = productDAO.getProduct(id);
		if (dbProduct.isPresent()) {
			LOGGER.debug("update product is present dbProduct {}", dbProduct.get().toString());
			ProductEntity productEntity = dbProduct.get();
			productEntity.setPrice(request.getPricing().getValue());
			productEntity.setCurrenc_Code(request.getPricing().getCurrency_code());
			productDAO.saveProduct(productEntity);
		} else {
			throw new ResourceNotFoundException("product not available for ID "+id);
		}

		LOGGER.debug("request after update {}", request);

		return request;

	}

	public Product getPRoductDetail(long id) {

		LOGGER.info("inside get service {}", id);
		String title;
		Product product = new Product();
		ProductEntity productEntity;
		String json = null;

		ResponseEntity<String> response = productAPIDao.getAPIResponse(id);

		if (response.getStatusCodeValue() != 200) {
			LOGGER.error(response.getStatusCode().getReasonPhrase().toString());
			throw new InternalServerErrorException("Response received from API is not 200");
		}

		json = response.getBody().toString();

		try {
			JSONObject obj = new JSONObject(json);
			JSONObject products = obj.getJSONObject("product");
			JSONObject item = products.getJSONObject("item");
			JSONObject product_description = item.getJSONObject("product_description");
			title = product_description.getString("title");
		} catch (JSONException e) {
			LOGGER.error("Parsing error :: {}", e);
			throw new JsonProsessingException("Json parse exception ::", e.getCause());
		}

		LOGGER.info("Product name from api:: {}", title);

		Optional<ProductEntity> dbProduct = productDAO.getProduct(id);

		LOGGER.info("record is present in DB :: {}", dbProduct.isPresent());
		if (dbProduct.isPresent()) {
			productEntity = dbProduct.get();
			LOGGER.info("fetched id successfully {}", productEntity.getId());
			Pricing pricing = new Pricing();
			product.setId(id);
			product.setName(title);
			pricing.setCurrency_code(productEntity.getCurrenc_Code());
			pricing.setValue(productEntity.getPrice());
			product.setPricing(pricing);
		} else {
			throw new ResourceNotFoundException("product not available in DB:: "+id);
		}

		return product;

	}

}
