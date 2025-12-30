package com.soundscape.reviews.repository;

import com.soundscape.reviews.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
