package com.gmm.restaurants.services;

import com.gmm.restaurants.model.api.BookingModel;
import com.gmm.restaurants.model.api.BookingRequestModel;
import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import java.util.List;

public interface BookingService {

    BookingModel get(String userName, String userRole, String restaurantId, String bookingId);

    List<BookingModel> getList(String userName, String userRole, String restaurantId);

    BookingModel create(String userName, String userRole, String restaurantId, BookingRequestModel request);

    void delete(String userName, String userRole, String restaurantId, String bookingId);

}
