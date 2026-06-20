package com.example.staffrating;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.staffrating.controller.StaffRatingController;
import com.example.staffrating.model.RoleType;
import com.example.staffrating.model.StaffRating;
import com.example.staffrating.repository.StaffRatingRepository;

@WebMvcTest(StaffRatingController.class)
public class StaffRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
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
    void getIndexReturnsPageWithRatingsModel() throws Exception {
        when(staffRatingRepository.findAll()).thenReturn(List.of(createRating()));

        mockMvc.perform(get("/ratings"))
                .andExpect(status().isOk())
                .andExpect(view().name("ratings/index"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attribute("ratings", hasSize(1)));
    }

    @Test
    void postCreateSuccessRedirectsToRatings() throws Exception {
        when(staffRatingRepository.existsByEmail("alex@example.com")).thenReturn(false);
        when(staffRatingRepository.save(any(StaffRating.class))).thenReturn(createRating());

        mockMvc.perform(post("/ratings")
                .param("name", "Alex Smith")
                .param("email", "alex@example.com")
                .param("roleType", "TA")
                .param("clarity", "8")
                .param("niceness", "9")
                .param("knowledgeableScore", "7")
                .param("comment", "Helpful during tutorials."))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings"));

        verify(staffRatingRepository).save(any(StaffRating.class));
    }

    @Test
    void postCreateFailureReturnsFormWithErrors() throws Exception {
        mockMvc.perform(post("/ratings")
                .param("name", "")
                .param("email", "bademail")
                .param("roleType", "TA")
                .param("clarity", "11")
                .param("niceness", "0")
                .param("knowledgeableScore", "7")
                .param("comment", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("ratings/form"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("roleTypes"))
                .andExpect(model().attributeExists("formTitle"))
                .andExpect(model().attributeExists("formAction"));
    }
}