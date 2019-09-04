package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.messages.ProductNotFoundException;
import com.udacity.course3.reviews.messages.ReviewNotFoundException;
import com.udacity.course3.reviews.model.*;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewDRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    /**
     * Wires JPA repositories
     */
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewDRepository reviewDRepository;


    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @Valid @RequestBody Comment comment) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Review review = optionalReview.orElseThrow(ReviewNotFoundException::new);
        comment.setReview(review);

        comment.setCreationtime(new Date());
        comment = commentRepository.save(comment);

        //save to mongodb
        Optional<ReviewD> optionalReviewD = reviewDRepository.findById(reviewId);
        ReviewD reviewD = optionalReviewD.get();

        CommentD commentD = new CommentD(comment);
        reviewD.getComments().add(commentD);
        reviewDRepository.save(reviewD);


        return ResponseEntity.ok(commentD);
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        //read comments from mongodb
        Optional<ReviewD> optionalReviewD = reviewDRepository.findById(reviewId);
        ReviewD reviewD = optionalReviewD.get();

        List<CommentD> comments = reviewD.getComments();
        return comments;
    }
}