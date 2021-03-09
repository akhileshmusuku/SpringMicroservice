package com.myretail.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.myretail.product.dbentity.ProductEntity;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, Long>{

}
