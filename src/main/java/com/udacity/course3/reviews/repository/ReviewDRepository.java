package com.udacity.course3.reviews.repository;


import com.udacity.course3.reviews.model.ReviewD;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDRepository extends MongoRepository<ReviewD, Integer> {
    List<ReviewD> findByProductId(int productId);
}
