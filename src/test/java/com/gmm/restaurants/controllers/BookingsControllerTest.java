package com.gmm.restaurants.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.BookingModel;
import com.gmm.restaurants.model.api.BookingRequestModel;
import com.gmm.restaurants.services.BookingService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class BookingsControllerTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    @Mock
    private BookingService service;

    @InjectMocks
    private BookingsController controller;

    @Before
    public void setUp() {
        this.controller = new BookingsController(service);
    }

    @Test
    @DisplayName("Get Bookings List Ok")
    public void shouldGetBookingsListOK() {
        List<BookingModel> mockData = this.generateBookingList();
        when(service.getList(any())).thenReturn(mockData);

        ResponseEntity<List<BookingModel>> response = controller.getRestaurantBookings(UUID.randomUUID());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(mockData.size());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Get exception when service fails")
    public void shouldGetExceptionWhenServiceFail() {
        when(service.getList(any())).thenThrow(new NullPointerException());
         controller.getRestaurantBookings(UUID.randomUUID());
    }


    @Test
    @DisplayName("Create Booking Ok")
    public void shouldCreateBookingOK() {
        BookingModel mockData = this.generateBooking();
        when(service.create(anyString(), any())).thenReturn(mockData);

        ResponseEntity<BookingModel> response = controller.createRestaurantBooking(UUID.randomUUID(), this.generateBookingRequest());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(mockData.getId());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Get exception when create service fails")
    public void shouldGetExceptionWhenServiceCreateFail() {
        when(service.create(anyString(), any())).thenThrow(new NullPointerException());
        controller.createRestaurantBooking(UUID.randomUUID(), this.generateBookingRequest());
    }

    @Test
    @DisplayName("Get Booking by Id Ok")
    public void shouldGetBookingsByIdOK() {
        BookingModel mockData = this.generateBooking();
        when(service.get(anyString(), anyString())).thenReturn(mockData);

        ResponseEntity<BookingModel> response = controller.getBookingById(UUID.randomUUID(), UUID.randomUUID());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(mockData.getId());
    }

    @Test(expected = NotFoundException.class)
    @DisplayName("Get exception when get service not founds id")
    public void shouldGetNotFoundExceptionWhenGetByIdNotFound() {
        when(service.get(anyString(), anyString())).thenThrow(new NotFoundException("restaurant", UUID.randomUUID().toString()));
        controller.getBookingById(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Get exception when get service fails")
    public void shouldGetExceptionWhenServiceGetFail() {
        when(service.get(anyString(), anyString())).thenThrow(new NullPointerException());
        controller.getBookingById(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test
    @DisplayName("Delete Booking by Id Ok")
    public void shouldDeleteRestaurantsByIdOK() {

        controller.cancelBooking(UUID.randomUUID(), UUID.randomUUID());
        verify(service).delete(anyString(), anyString());
    }

    private List<BookingModel> generateBookingList() {
        return factory.manufacturePojo(ArrayList.class, BookingModel.class);
    }

    private BookingModel generateBooking() {
        return factory.manufacturePojo(BookingModel.class);
    }

    private BookingRequestModel generateBookingRequest() {
        return factory.manufacturePojo(BookingRequestModel.class);
    }
}
