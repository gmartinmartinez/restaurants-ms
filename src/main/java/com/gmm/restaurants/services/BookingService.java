package com.gmm.restaurants.services;

import com.gmm.restaurants.model.api.BookingModel;
import com.gmm.restaurants.model.api.BookingRequestModel;
import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import java.util.List;

public interface BookingService {

    BookingModel get(String restaurantId, String bookingId);

    List<BookingModel> getList(String restaurantId);

    BookingModel create(String restaurantId, BookingRequestModel request);

    void delete(String restaurantId, String bookingId);

}
