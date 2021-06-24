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
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
    public ReviewModel get(Integer restaurantId, Integer reviewId) {
        try {
            Integer id = restaurantRepository.getOne(restaurantId).getId();
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
    public List<ReviewModel> getList(Integer restaurantId) {
        try {
            RestaurantEntity entity = restaurantRepository.getOne(restaurantId);
            if(entity!=null && entity.getReviews()!=null ) {
                return entity.getReviews().stream().map(this::mapEntityToModel).collect(Collectors.toList());
            }
            return new ArrayList<>();
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("restaurant", restaurantId);
        }
    }

    @Override
    public ReviewModel create(Integer restaurantId, ReviewRequestModel request) {
        try {
            restaurantRepository.getOne(restaurantId);
            return mapEntityToModel(reviewRepository.saveAndFlush(mapRequestToEntity(request, restaurantId)));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("restaurant", restaurantId);
        }
    }

    @Override
    public ReviewModel update(Integer restaurantId, Integer reviewId, ReviewRequestModel request) {
        ReviewEntity entity = reviewRepository.findByIdAndRestaurant(reviewId, restaurantId);
        if (entity!=null) {
            ReviewEntity updateEntity = mapRequestToEntity(request, restaurantId);
            updateEntity.setId(entity.getId());
            return mapEntityToModel(reviewRepository.saveAndFlush(updateEntity));
        } else {
            throw new NotFoundException("review", reviewId);
        }
    }

    private ReviewModel mapEntityToModel(ReviewEntity entity){
        if(entity!=null){
            return ReviewModel.builder()
                .id(entity.getId())
                .creationDate(entity.getCreationDate())
                .description(entity.getDescription())
                .senderNick(entity.getNick())
                .stars(entity.getStars())
                .title(entity.getTitle())
                .build();
        }
        return null;
    }

    private ReviewEntity mapRequestToEntity(ReviewRequestModel request, Integer id){
        return ReviewEntity.builder()
            .creationDate(LocalDateTime.now())
            .description(request.getDescription())
            .restaurant(id)
            .nick(request.getSenderNick())
            .stars(request.getStars().getValue())
            .title(request.getTitle())
            .build();
    }
}
