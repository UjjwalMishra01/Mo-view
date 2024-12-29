package com.ujjwal.Movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable String reviewId, @RequestBody Map<String, String> payload) {
        String newReviewBody = payload.get("reviewBody");

        Optional<Review> updatedReview = service.updateReview(reviewId, newReviewBody);

        if (updatedReview.isPresent()) {
            return new ResponseEntity<>(updatedReview.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Review not found
        }
    }
}
