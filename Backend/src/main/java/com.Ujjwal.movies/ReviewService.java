package com.ujjwal.Movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId) {
        Review review = repository.insert(new Review(reviewBody, LocalDateTime.now(), LocalDateTime.now()));

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewId").value(review.getId())) // Corrected the field name to match your Movie class
                .first();
        return review;
    }


    public Optional<Review> updateReview(String reviewId, String newReviewBody) {
        Optional<Review> optionalReview = repository.findById(new ObjectId(reviewId));

        if (optionalReview.isPresent()) {
            Review existingReview = optionalReview.get();
            existingReview.setBody(newReviewBody); // Update the review body
            existingReview.setUpdated(LocalDateTime.now()); // Update the 'updated' timestamp

            Review updatedReview = repository.save(existingReview); // Save the updated review
            return Optional.of(updatedReview);
        }

        return Optional.empty(); // If review not found
    }
}
