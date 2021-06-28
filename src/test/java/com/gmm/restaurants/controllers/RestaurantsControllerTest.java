package com.gmm.restaurants.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import com.gmm.restaurants.services.RestaurantService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantsControllerTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    @Mock
    private RestaurantService service;

    @InjectMocks
    private RestaurantsController controller;

    @Before
    public void setUp() {
        this.controller = new RestaurantsController(service);
    }

    //GET /restaurants
    @Test
    public void shouldGetRestaurantsListOK() {
        List<RestaurantModel> mockData = this.generateRestaurantList();
        when(service.getList()).thenReturn(mockData);

        ResponseEntity<List<RestaurantModel>> response = controller.getRestaurants();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(mockData.size());
    }

    @Test(expected = NullPointerException.class)
    public void shouldGetExceptionWhenServiceFail() {
        when(service.getList()).thenThrow(new NullPointerException());
         controller.getRestaurants();
    }

    //POST /restaurants

    @Test
    public void shouldCreateRestaurantOK() {
        RestaurantModel mockData = this.generateRestaurant();
        when(service.create(any())).thenReturn(mockData);

        ResponseEntity<RestaurantModel> response = controller.createRestaurant(this.generateRestaurantRequest());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(mockData.getId());
    }

    @Test(expected = NullPointerException.class)
    public void shouldGetExceptionWhenServiceCreateFail() {
        when(service.create(any())).thenThrow(new NullPointerException());
        controller.createRestaurant(this.generateRestaurantRequest());
    }

    //GET /restaurants/{restaurantId}
    @Test
    public void shouldGetRestaurantsByIdOK() {
        RestaurantModel mockData = this.generateRestaurant();
        when(service.get(any())).thenReturn(mockData);

        ResponseEntity<RestaurantModel> response = controller.getRestaurantById(UUID.randomUUID());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(mockData.getId());
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetByIdNotFound() {
        when(service.get(any())).thenThrow(new NotFoundException("restaurant", UUID.randomUUID().toString()));
        controller.getRestaurantById(UUID.randomUUID());
    }

    @Test(expected = NullPointerException.class)
    public void shouldGetExceptionWhenServiceGetFail() {
        when(service.get(any())).thenThrow(new NullPointerException());
        controller.getRestaurantById(UUID.randomUUID());
    }

    //DELETE /restaurants/{restaurantId}
    @Test
    public void shouldDeleteRestaurantsByIdOK() {

        controller.deleteRestaurant(UUID.randomUUID());
        verify(service).delete(any());
    }

    private List<RestaurantModel> generateRestaurantList() {
        return factory.manufacturePojo(ArrayList.class, RestaurantModel.class);
    }

    private RestaurantModel generateRestaurant() {
        return factory.manufacturePojo(RestaurantModel.class);
    }

    private RestaurantRequestModel generateRestaurantRequest() {
        return factory.manufacturePojo(RestaurantRequestModel.class);
    }
}
