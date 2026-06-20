package com.example.staffrating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.staffrating.model.RoleType;
import com.example.staffrating.model.StaffRating;
import com.example.staffrating.repository.StaffRatingRepository;

@DataJpaTest
public class StaffRatingRepositoryTest {

    @Autowired
    private StaffRatingRepository staffRatingRepository;

    private StaffRating createRating() {
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
    void savingAndRetrievingEntryWorks() {
        StaffRating savedRating = staffRatingRepository.save(createRating());

        Optional<StaffRating> foundRating = staffRatingRepository.findById(savedRating.getId());

        assertTrue(foundRating.isPresent());
        assertEquals("Alex Smith", foundRating.get().getName());
        assertEquals("alex@example.com", foundRating.get().getEmail());
        assertEquals(RoleType.TA, foundRating.get().getRoleType());
    }

    @Test
    void deleteRemovesEntry() {
        StaffRating savedRating = staffRatingRepository.save(createRating());
        Long id = savedRating.getId();

        staffRatingRepository.deleteById(id);

        assertFalse(staffRatingRepository.findById(id).isPresent());
    }
}
