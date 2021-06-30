package com.gmm.restaurants.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gmm.restaurants.exceptions.ForbiddenResourceException;
import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import com.gmm.restaurants.model.api.ReviewModel;
import com.gmm.restaurants.model.api.ReviewRequestModel;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.model.entities.ReviewEntity;
import com.gmm.restaurants.repositories.RestaurantRepository;
import com.gmm.restaurants.repositories.ReviewRepository;
import com.gmm.restaurants.utils.EntityGeneratorUtils;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private final RestaurantEntity restaurant = EntityGeneratorUtils.createRestaurant();
    private final ReviewEntity review = EntityGeneratorUtils.createReview();
    private final ReviewRequestModel request = factory.manufacturePojo(ReviewRequestModel.class);

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl service;

    @Before
    public void setUp() {
        this.service = new ReviewServiceImpl(restaurantRepository, reviewRepository);
    }

    @Test
    public void shouldGetReviewList() {
        RestaurantEntity mockData = restaurant;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);

        List<ReviewModel> response = service.getList(UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        if (mockData.getReviews()!=null)
            assertThat(response.size()).isEqualTo(mockData.getReviews().size());
        else
            assertThat(response.size()).isEqualTo(0);
    }

    @Test
    public void shouldGetReviewById() {
        ReviewEntity mockData = review;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(reviewRepository.getOne(any())).thenReturn(mockData);

        ReviewModel response = service.get(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getDescription()).isEqualTo(mockData.getDescription());
        assertThat(response.getStars()).isEqualTo(mockData.getStars());

    }

    @Test
    public void shouldCreateReview() {
        ReviewEntity mockData = review;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(reviewRepository.saveAndFlush(any())).thenReturn(mockData);

        ReviewModel response = service.create("admin", "adminrole", UUID.randomUUID().toString(), request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getStars()).isEqualTo(mockData.getStars());
        assertThat(response.getTitle()).isEqualTo(mockData.getTitle());
    }

    @Test
    public void shouldCreateReviewCust() {
        ReviewEntity mockData = review;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(reviewRepository.saveAndFlush(any())).thenReturn(mockData);

        ReviewModel response = service.create("user", "custrole", UUID.randomUUID().toString(), request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getStars()).isEqualTo(mockData.getStars());
        assertThat(response.getTitle()).isEqualTo(mockData.getTitle());
    }

    @Test
    public void shouldUpdateReview() {
        ReviewEntity mockData = review;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(reviewRepository.getOne(any())).thenReturn(mockData);
        when(reviewRepository.saveAndFlush(any())).thenReturn(mockData);

        String id = UUID.randomUUID().toString();
        ReviewModel response = service.update("admin", "adminrole", id, id, request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getDescription()).isEqualTo(mockData.getDescription());
        assertThat(response.getStars()).isEqualTo(mockData.getStars());
    }

    @Test
    public void shouldUpdateReviewCust() {
        ReviewEntity mockData = review;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(reviewRepository.getOne(any())).thenReturn(mockData);
        when(reviewRepository.saveAndFlush(any())).thenReturn(mockData);

        String id = UUID.randomUUID().toString();
        ReviewModel response = service.update("user", "custrole", id, id, request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getDescription()).isEqualTo(mockData.getDescription());
        assertThat(response.getStars()).isEqualTo(mockData.getStars());
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetRestaurantById() {
        when(restaurantRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.get(UUID.randomUUID().toString(), UUID.randomUUID().toString());

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetById() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(reviewRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.get(UUID.randomUUID().toString(), UUID.randomUUID().toString());

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetRestaurantList() {
        when(restaurantRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.getList(UUID.randomUUID().toString());

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenCreate() {
        when(restaurantRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.create("admin", "adminrole", UUID.randomUUID().toString(), this.request);

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundRestaurantExceptionWhenUpdateById() {
        when(restaurantRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.update("admin", "adminrole", UUID.randomUUID().toString(), UUID.randomUUID().toString(), request);
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenUpdateById() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(reviewRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.update("admin", "adminrole", UUID.randomUUID().toString(), UUID.randomUUID().toString(), request);
    }


    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenCreate() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        service.create("user", "restrole", UUID.randomUUID().toString(), this.request);

    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenUpdate() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        service.update("user", "restrole", UUID.randomUUID().toString(), UUID.randomUUID().toString(), this.request);

    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenUpdateCustomer() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(reviewRepository.getOne(any())).thenReturn(review);
        service.update("user2", "custrole", UUID.randomUUID().toString(), UUID.randomUUID().toString(), this.request);
    }

    @Test
    public void shouldGetReviewListEmpty() {
        restaurant.setReviews(null);
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);

        List<ReviewModel> response = service.getList(UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        assertThat(response).isEmpty();
    }

}
