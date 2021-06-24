package com.gmm.restaurants.services;

import com.gmm.restaurants.model.api.ReviewModel;
import com.gmm.restaurants.model.api.ReviewRequestModel;
import java.util.List;

public interface ReviewService {

    ReviewModel get(Integer restaurantId, Integer reviewId);

    List<ReviewModel> getList(Integer restaurantId);

    ReviewModel create(Integer restaurantId, ReviewRequestModel request);

    ReviewModel update(Integer restaurantId, Integer reviewId, ReviewRequestModel request);

}
