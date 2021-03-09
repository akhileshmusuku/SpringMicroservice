package com.myretail.product.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myretail.product.exception.ExceptionResponse;
import com.myretail.product.exception.InternalServerErrorException;
import com.myretail.product.exception.ResourceNotFoundException;
import com.myretail.product.model.Product;
import com.myretail.product.service.ProductService;

@RestController
public class ProductController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	
	@PutMapping(value = "/products/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<Product> saveProduct(@RequestBody Product request, @PathVariable long id) {
		
		LOGGER.info("request payload {}", request);
		
		Product response= productService.updateProduct(request, id);
		LOGGER.debug("response payload {}", response);
		return new ResponseEntity<Product>(response,HttpStatus.OK);
		
	}

	@GetMapping(value = "/products/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Product> getProduct(@PathVariable long id) {

		LOGGER.debug("request id {}", id);

		Product response= productService.getPRoductDetail(id);
		LOGGER.debug("response payload {}", response);
		return new ResponseEntity<Product>(response,HttpStatus.OK);

	}
	
	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<ExceptionResponse> handle500(InternalServerErrorException e){
		ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<ExceptionResponse> handleBackendNotResponding(JsonProcessingException e){
		ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.EXPECTATION_FAILED);
		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException e){
		ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(), HttpStatus.NOT_FOUND.toString());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception e){
		ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
