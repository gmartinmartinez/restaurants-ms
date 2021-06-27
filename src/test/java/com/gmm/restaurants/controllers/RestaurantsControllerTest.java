package com.gmm.restaurants.controllers;

import com.gmm.restaurants.services.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantsControllerTest {

    @Mock
    private RestaurantService service;

    @InjectMocks
    private RestaurantsController controller;

    //GET /restaurants
    @Test
    public void shouldGetRestaurantsListOK() {

    }

    @Test
    public void shouldGet500ResponseWhenGet() {

    }

    //POST /restaurants

    @Test
    public void shouldCreateRestaurantOK() {

    }

    @Test
    public void shouldGet400ResponseWhenCreateRestaurant() {

    }

    @Test
    public void shouldGet500ResponseWhenCreateRestaurant() {

    }

    //GET /restaurants/{restaurantId}
    @Test
    public void shouldGetRestaurantsByIdOK() {

    }

    @Test
    public void shouldGet404ResponseWhenGetById() {

    }

    @Test
    public void shouldGet400ResponseWhenGetById() {

    }

    @Test
    public void shouldGet500ResponseWhenGetById() {

    }

    //DELETE /restaurants/{restaurantId}
    @Test
    public void shouldDeleteRestaurantsByIdOK() {

    }

    @Test
    public void shouldGet404ResponseWhenDeleteById() {

    }

    @Test
    public void shouldGet400ResponseWhenDeleteById() {

    }

    @Test
    public void shouldGet500ResponseWhenDeleteById() {

    }
}
