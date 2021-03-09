package com.myretail.product;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.spring.commons.config.AsyncConfig.withTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myretail.product.config.Config;
import com.myretail.product.dbentity.ProductEntity;
import com.myretail.product.exception.ExceptionResponse;
import com.myretail.product.exception.JsonProsessingException;
import com.myretail.product.model.Pricing;
import com.myretail.product.model.Product;
import com.myretail.product.repository.ProductRepository;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductApplicationTest {

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private ProductRepository mockRepository;

	private Product product;

	private ProductEntity productEntity;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		RestAssuredMockMvc.webAppContextSetup(context);
		RestAssuredMockMvc.config().asyncConfig(withTimeout(2, TimeUnit.SECONDS));

		Pricing pricing = new Pricing(13.8, "USD");
		product = new Product(13860428, "The Big Lebowski (Blu-ray)", pricing);

		productEntity = new ProductEntity();

		productEntity.setId(13860428);
		productEntity.setCurrenc_Code("USD");
		productEntity.setPrice(13.8);

	}

	@Test
	public void testGetProductController() {
		String apiOutput = "{\"product\":{\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\"}}}}";

		ResponseEntity<String> apiResponse = new ResponseEntity<>(apiOutput, HttpStatus.OK);

		Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(String.class))).thenReturn(apiResponse);

		Mockito.when(mockRepository.findById((long) 13860428)).thenReturn(Optional.of(productEntity));

		Product response = given().when().get("/products/13860428").then().statusCode(200).extract().as(Product.class);

		Assert.assertTrue(response.getId() == 13860428);
		Assert.assertTrue(response.getName().equals("The Big Lebowski (Blu-ray)"));
		Assert.assertTrue(response.getPricing().getCurrency_code().equals("USD"));
		Assert.assertTrue(response.getPricing().getValue() == 13.8);

	}
	
	@Test
	public void testGetProductController_jsonParse_failure() {
		String apiOutput = "{\"product\":{\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\"}}}}";

		ResponseEntity<String> apiResponse = new ResponseEntity<>(apiOutput, HttpStatus.OK);

		Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(String.class))).thenReturn(apiResponse);

		Mockito.when(mockRepository.findById((long) 13860428)).thenReturn(Optional.of(productEntity));
		
		final JSONObject jsonObject = Mockito.mock(JSONObject.class);
		
		Mockito.when(jsonObject.getString("title")).thenThrow(new JsonProsessingException(new String("message")));

		Product response = given().when().get("/products/13860428").then().statusCode(200).extract().as(Product.class);

		Assert.assertTrue(response.getId() == 13860428);
		Assert.assertTrue(response.getName().equals("The Big Lebowski (Blu-ray)"));
		Assert.assertTrue(response.getPricing().getCurrency_code().equals("USD"));
		Assert.assertTrue(response.getPricing().getValue() == 13.8);

	}
	
	@Test
	public void testGetProductController_api_failure() {
		String apiOutput = "{\"product\":{\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\"}}}}";

		ResponseEntity<String> apiResponse = new ResponseEntity<>(apiOutput, HttpStatus.OK);

		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(String.class)))
		.thenThrow(new RuntimeException());

		Mockito.when(mockRepository.findById((long) 13860428)).thenReturn(Optional.of(productEntity));

		ExceptionResponse response = given().when().get("/products/13860428").then().statusCode(500).extract().as(ExceptionResponse.class);

		Assert.assertTrue(response.getStatus().equals("500 INTERNAL_SERVER_ERROR"));

	}

	@Test
	public void testPutProductController() {

		Mockito.when(mockRepository.findById((long) 13860428)).thenReturn(Optional.of(productEntity));

		Mockito.when(mockRepository.save(Mockito.any(ProductEntity.class))).thenReturn(productEntity);

		Product response = given().contentType(ContentType.JSON).body(product).when()
				.put("/products/13860428").then().statusCode(200).extract().as(Product.class);
		
		Assert.assertTrue(response.getId() == 13860428);
		Assert.assertTrue(response.getName().equals("The Big Lebowski (Blu-ray)"));
		Assert.assertTrue(response.getPricing().getCurrency_code().equals("USD"));
		Assert.assertTrue(response.getPricing().getValue() == 13.8);

	}
	
	@Test
	public void test_config() {
		Config conf = new Config();
		Config spy = Mockito.spy(conf);
		
		RestTemplate template = new RestTemplate();
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		Mockito.when(spy.restTemplate(restTemplateBuilder)).thenReturn(template);
		RestTemplate resTemplate = conf.restTemplate(restTemplateBuilder);
		Assert.assertTrue(resTemplate instanceof RestTemplate);
		
	}
	
	@Test
    public void testExceptionResponse() {
        ExceptionResponse exception = new ExceptionResponse();
        Date date = new Date();
        exception.setDate(date);
        exception.setMessage("test");
        exception.setStatus("200");
        assertTrue(exception.getDate().compareTo(date) == 0);
        assertTrue(exception.getMessage().equals("test"));
        assertTrue(exception.getStatus().equals("200"));
        
    }
	
	@Test
	public void testResourceNotFoundException() {
		
	}
	
 
  
	@After
	public void tearDown() {
		RestAssuredMockMvc.reset();
	}

}
