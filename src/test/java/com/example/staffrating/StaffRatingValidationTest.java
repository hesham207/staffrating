package com.example.staffrating;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.staffrating.model.RoleType;
import com.example.staffrating.model.StaffRating;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class StaffRatingValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private StaffRating validRating() {
        StaffRating rating = new StaffRating();
        rating.setName("Alex Smith");
        rating.setEmail("alex@example.com");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(8);
        rating.setNiceness(9);
        rating.setKnowledgeableScore(7);
        rating.setComment("Helpful during tutorials.");
        return rating;
    }

    @Test
    void invalidEmailIsRejected() {
        StaffRating rating = validRating();
        rating.setEmail("bademail");

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void outOfRangeScoreIsRejected() {
        StaffRating rating = validRating();
        rating.setClarity(11);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("clarity")));
    }

    @Test
    void missingRequiredFieldsAreRejected() {
        StaffRating rating = validRating();
        rating.setName("");
        rating.setEmail("");
        rating.setRoleType(null);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")));

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("roleType")));
    }
}