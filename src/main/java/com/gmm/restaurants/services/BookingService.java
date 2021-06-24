package com.gmm.restaurants.services;

import com.gmm.restaurants.model.api.BookingModel;
import com.gmm.restaurants.model.api.BookingRequestModel;
import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import java.util.List;

public interface BookingService {

    BookingModel get(Integer restaurantId, Integer bookingId);

    List<BookingModel> getList(Integer restaurantId);

    BookingModel create(Integer restaurantId, BookingRequestModel request);

    void delete(Integer restaurantId, Integer bookingId);

}
