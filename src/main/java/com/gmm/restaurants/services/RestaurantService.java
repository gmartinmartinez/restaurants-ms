package com.gmm.restaurants.services;

import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import java.util.List;

public interface RestaurantService {

    RestaurantModel get(Integer restaurantId);

    List<RestaurantModel> getList();

    RestaurantModel create(RestaurantRequestModel restaurant);

    void delete(Integer id);

}
