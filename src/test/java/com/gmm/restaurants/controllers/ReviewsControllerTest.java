package com.gmm.restaurants.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.BookingModel;
import com.gmm.restaurants.model.api.BookingRequestModel;
import com.gmm.restaurants.model.api.ReviewModel;
import com.gmm.restaurants.model.api.ReviewRequestModel;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.model.entities.ReviewEntity;
import com.gmm.restaurants.services.BookingService;
import com.gmm.restaurants.services.ReviewService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReviewsControllerTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private final ReviewModel review = generateReview();
    private final List<ReviewModel> reviewList = Arrays.asList(review);
    private final ReviewRequestModel request = generateReviewRequest();

    @Mock
    private ReviewService service;

    @InjectMocks
    private ReviewsController controller;

    @Before
    public void setUp() {
        this.controller = new ReviewsController(service);
    }

    @Test
    public void shouldGetReviewsListOK() {
        List<ReviewModel> mockData = this.reviewList;
        when(service.getList(any())).thenReturn(mockData);

        ResponseEntity<List<ReviewModel>> response = controller.getRestaurantReviews(UUID.randomUUID());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(mockData.size());
    }

    @Test(expected = NullPointerException.class)
    public void shouldGetExceptionWhenServiceFail() {
        when(service.getList(any())).thenThrow(new NullPointerException());
         controller.getRestaurantReviews(UUID.randomUUID());
    }


    @Test
    public void shouldCreateReviewOK() {
        ReviewModel mockData = this.review;
        when(service.create(anyString(), anyString(), anyString(), any())).thenReturn(mockData);

        ResponseEntity<ReviewModel> response = controller.createRestaurantReview("user", "role", UUID.randomUUID(), this.request);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(mockData.getId());
    }

    @Test(expected = NullPointerException.class)
    public void shouldGetExceptionWhenServiceCreateFail() {
        when(service.create(anyString(), anyString(), anyString(), any())).thenThrow(new NullPointerException());
        controller.createRestaurantReview("user", "role", UUID.randomUUID(), this.request);
    }

    @Test
    public void shouldGetReviewsByIdOK() {
        ReviewModel mockData = this.review;
        when(service.get(anyString(), anyString())).thenReturn(mockData);

        ResponseEntity<ReviewModel> response = controller.getRestaurantReviewById(UUID.randomUUID(), UUID.randomUUID());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(mockData.getId());
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetByIdNotFound() {
        when(service.get(anyString(), anyString())).thenThrow(new NotFoundException("restaurant", UUID.randomUUID().toString()));
        controller.getRestaurantReviewById(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test(expected = NullPointerException.class)
    public void shouldGetExceptionWhenServiceGetFail() {
        when(service.get(anyString(), anyString())).thenThrow(new NullPointerException());
        controller.getRestaurantReviewById(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test
    public void shouldDeleteRestaurantsByIdOK() {
        ReviewModel mockData = this.review;
        when(service.update(anyString(), anyString(), anyString(), anyString(), any())).thenReturn(mockData);
        ResponseEntity<ReviewModel> response =  controller.updateRestaurantReview("user", "role", UUID.randomUUID(), UUID.randomUUID(), request);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(mockData.getId());
    }


    private ReviewModel generateReview() {
        return factory.manufacturePojo(ReviewModel.class);
    }

    private ReviewRequestModel generateReviewRequest() {
        return factory.manufacturePojo(ReviewRequestModel.class);
    }
}
