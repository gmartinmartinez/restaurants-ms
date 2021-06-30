package com.gmm.restaurants.utils;

import com.gmm.restaurants.model.entities.BookingEntity;
import com.gmm.restaurants.model.entities.DishEntity;
import com.gmm.restaurants.model.entities.MenuEntity;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.model.entities.ReviewEntity;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public final class EntityGeneratorUtils {

    public static DishEntity createDish() {
        return DishEntity.builder()
            .id(UUID.randomUUID().toString())
            .description("DISH 1")
            .isVegetarian(true)
            .price(3.5)
            .build();
    }

    public static BookingEntity createBooking() {
        return BookingEntity.builder()
            .id(UUID.randomUUID().toString())
            .restaurant(UUID.randomUUID().toString())
            .phone("627329011")
            .peopleNumber(3)
            .name("Pedro Diaz")
            .email("pedro@gmail.com")
            .creationDate(LocalDateTime.now())
            .comments("")
            .bookingDate(LocalDateTime.now())
            .user("user")
            .role("custrole")
            .build();
    }

    public static MenuEntity createMenu() {

        return MenuEntity.builder()
            .id(UUID.randomUUID().toString())
            .description("MENU 1")
            .availability("summer 2021")
            .dishes(Arrays.asList(createDish()))
            .build();
    }

    public static ReviewEntity createReview() {
        return ReviewEntity.builder()
            .id(UUID.randomUUID().toString())
            .title("REVIEW 1")
            .stars(3)
            .nick("user003")
            .creationDate(LocalDateTime.now())
            .description("Very good restaurant")
            .user("user")
            .role("custrole")
            .restaurant(UUID.randomUUID().toString())
            .build();
    }

    public static RestaurantEntity createRestaurant (){
        return RestaurantEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("RESTAURANT 1")
            .averageAmount(30.0)
            .address("Calle margaritas, 14")
            .city("Mostoles")
            .email("rest1@gmail.com")
            .phone("627329019")
            .country("SP")
            .description("Restaurante gastrobar con terraza cerca de la renfe")
            .foodType("Mediterranea")
            .province("Madrid")
            .website("http://restaurante.mostoles.com")
            .zipcode("28925")
            .user("user")
            .role("restrole")
            .bookings(Arrays.asList(createBooking()))
            .reviews(Arrays.asList(createReview()))
            .menus(Arrays.asList(createMenu()))
            .build();

    }

}
