package com.gmm.restaurants.services;

import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.ReviewModel;
import com.gmm.restaurants.model.api.ReviewRequestModel;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.model.entities.ReviewEntity;
import com.gmm.restaurants.repositories.RestaurantRepository;
import com.gmm.restaurants.repositories.ReviewRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService{

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl (RestaurantRepository restaurantRepository, ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public ReviewModel get(String restaurantId, String reviewId) {
        try {
            restaurantRepository.getOne(restaurantId).getAddress();
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("restaurant", restaurantId);
        }

        try {
            return mapEntityToModel(reviewRepository.getOne(reviewId));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("review", reviewId);
        }

    }

    @Override
    public List<ReviewModel> getList(String restaurantId) {
        try {
            RestaurantEntity entity = restaurantRepository.getOne(restaurantId);
            if(entity.getReviews()!=null ) {
                return entity.getReviews().stream().map(this::mapEntityToModel).collect(Collectors.toList());
            }
            return new ArrayList<>();
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("restaurant", restaurantId);
        }
    }

    @Override
    public ReviewModel create(String restaurantId, ReviewRequestModel request) {
        try {
            restaurantRepository.getOne(restaurantId).getAddress();
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("restaurant", restaurantId);
        }
        return mapEntityToModel(reviewRepository.saveAndFlush(mapRequestToEntity(request, restaurantId)));
    }

    @Override
    public ReviewModel update(String restaurantId, String reviewId, ReviewRequestModel request) {
        try {
            restaurantRepository.getOne(restaurantId).getAddress();
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("restaurant", restaurantId);
        }
        try {
            ReviewEntity entity = reviewRepository.getOne(reviewId);
            ReviewEntity updateEntity = mapRequestToEntity(request, restaurantId);
            updateEntity.setId(entity.getId());
            return mapEntityToModel(reviewRepository.saveAndFlush(updateEntity));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("review", reviewId);
        } catch (JpaObjectRetrievalFailureException e) {
            log.error(e.getMessage());
            throw new NotFoundException("review", reviewId);
        }
    }

    private ReviewModel mapEntityToModel(ReviewEntity entity){
            return ReviewModel.builder()
                .id(entity.getId())
                .creationDate(entity.getCreationDate())
                .description(entity.getDescription())
                .senderNick(entity.getNick())
                .stars(entity.getStars())
                .title(entity.getTitle())
                .build();
    }

    private ReviewEntity mapRequestToEntity(ReviewRequestModel request, String id){
        return ReviewEntity.builder()
            .id(UUID.randomUUID().toString())
            .creationDate(LocalDateTime.now())
            .description(request.getDescription())
            .restaurant(id)
            .nick(request.getSenderNick())
            .stars(request.getStars().getValue())
            .title(request.getTitle())
            .build();
    }
}
