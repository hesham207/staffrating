package com.example.staffrating.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.staffrating.model.StaffRating;

public interface StaffRatingRepository extends JpaRepository<StaffRating, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}