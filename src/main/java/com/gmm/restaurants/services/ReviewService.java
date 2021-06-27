package com.gmm.restaurants.services;

import com.gmm.restaurants.model.api.ReviewModel;
import com.gmm.restaurants.model.api.ReviewRequestModel;
import java.util.List;

public interface ReviewService {

    ReviewModel get(String restaurantId, String reviewId);

    List<ReviewModel> getList(String restaurantId);

    ReviewModel create(String restaurantId, ReviewRequestModel request);

    ReviewModel update(String restaurantId, String reviewId, ReviewRequestModel request);

}
