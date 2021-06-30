package com.gmm.restaurants.services;

import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import java.util.List;

public interface RestaurantService {

    RestaurantModel get(String restaurantId);

    List<RestaurantModel> getList();

    RestaurantModel create(String userName, String userRole, RestaurantRequestModel restaurant);

    void delete(String userName, String userRole, String id);

}
