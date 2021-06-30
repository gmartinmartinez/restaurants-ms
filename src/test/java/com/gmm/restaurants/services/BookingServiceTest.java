package com.gmm.restaurants.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gmm.restaurants.exceptions.ForbiddenResourceException;
import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.BookingModel;
import com.gmm.restaurants.model.api.BookingRequestModel;
import com.gmm.restaurants.model.api.ReviewModel;
import com.gmm.restaurants.model.entities.BookingEntity;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.repositories.BookingRepository;
import com.gmm.restaurants.repositories.RestaurantRepository;
import com.gmm.restaurants.utils.EntityGeneratorUtils;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private final RestaurantEntity restaurant = EntityGeneratorUtils.createRestaurant();
    private final BookingEntity booking = EntityGeneratorUtils.createBooking();
    private final BookingRequestModel request = factory.manufacturePojo(BookingRequestModel.class);

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl service;

    @Before
    public void setUp() {
        this.service = new BookingServiceImpl(restaurantRepository, bookingRepository);
    }

    @Test
    public void shouldGetBookingList() {
        RestaurantEntity mockData = restaurant;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);

        List<BookingModel> response = service.getList("admin", "adminrole",UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        if (mockData.getBookings()!=null)
            assertThat(response.size()).isEqualTo(mockData.getBookings().size());
        else
            assertThat(response.size()).isEqualTo(0);
    }

    @Test
    public void shouldGetBookingById() {
        BookingEntity mockData = booking;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.getOne(any())).thenReturn(mockData);

        BookingModel response = service.get("admin", "adminrole",UUID.randomUUID().toString(), UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getBookingDate()).isEqualTo(mockData.getBookingDate());
        assertThat(response.getName()).isEqualTo(mockData.getName());

    }

    @Test
    public void shouldGetBookingByIdRestRole() {
        BookingEntity mockData = booking;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.getOne(any())).thenReturn(mockData);

        BookingModel response = service.get("user", "restrole",UUID.randomUUID().toString(), UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getBookingDate()).isEqualTo(mockData.getBookingDate());
        assertThat(response.getName()).isEqualTo(mockData.getName());

    }

    @Test
    public void shouldGetBookingByIdCustomer() {
        BookingEntity mockData = booking;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.getOne(any())).thenReturn(mockData);

        BookingModel response = service.get("user", "custrole",UUID.randomUUID().toString(), UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getBookingDate()).isEqualTo(mockData.getBookingDate());
        assertThat(response.getName()).isEqualTo(mockData.getName());

    }

    @Test
    public void shouldCreateBooking() {
        BookingEntity mockData = booking;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.saveAndFlush(any())).thenReturn(mockData);

        BookingModel response = service.create("admin", "adminrole",UUID.randomUUID().toString(), request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getName()).isEqualTo(mockData.getName());
        assertThat(response.getComments()).isEqualTo(mockData.getComments());
    }

    @Test
    public void shouldCreateBookingCustomer() {
        BookingEntity mockData = booking;
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.saveAndFlush(any())).thenReturn(mockData);

        BookingModel response = service.create("user", "custrole",UUID.randomUUID().toString(), request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getName()).isEqualTo(mockData.getName());
        assertThat(response.getComments()).isEqualTo(mockData.getComments());
    }

    @Test
    public void shouldDeleteBooking() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.getOne(any())).thenReturn(booking);

        String id = UUID.randomUUID().toString();
        service.delete("admin", "adminrole",id, id);

        verify(restaurantRepository, times(1)).getOne(id);
        verify(bookingRepository, times(1)).getOne(id);
        verify(bookingRepository, times(1)).deleteById(id);
    }

    @Test
    public void shouldDeleteBookingRestRole() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.getOne(any())).thenReturn(booking);

        String id = UUID.randomUUID().toString();
        service.delete("user", "restrole",id, id);

        verify(restaurantRepository, times(1)).getOne(id);
        verify(bookingRepository, times(1)).getOne(id);
        verify(bookingRepository, times(1)).deleteById(id);
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetRestaurantById() {
        when(restaurantRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.get("admin", "adminrole",UUID.randomUUID().toString(), UUID.randomUUID().toString());

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetById() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.get("admin", "adminrole",UUID.randomUUID().toString(), UUID.randomUUID().toString());

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetRestaurantList() {
        when(restaurantRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.getList("admin", "adminrole",UUID.randomUUID().toString());

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenCreate() {
        when(restaurantRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.create("admin", "adminrole",UUID.randomUUID().toString(), this.request);

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundRestaurantExceptionWhenDeleteById() {
        when(restaurantRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.delete("admin", "adminrole",UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenDeleteById() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.delete("admin", "adminrole",UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenCreate() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        service.create("user2", "restrole",UUID.randomUUID().toString(), this.request);

    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenGetList() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        service.getList("user2", "custrole",UUID.randomUUID().toString());

    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenDelete() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        service.delete("user2", "restrole",UUID.randomUUID().toString(),UUID.randomUUID().toString());

    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenDeleteCustRole() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.getOne(any())).thenReturn(booking);
        service.delete("user2", "custrole",UUID.randomUUID().toString(),UUID.randomUUID().toString());

    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenGet() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        service.delete("user2", "restrole",UUID.randomUUID().toString(),UUID.randomUUID().toString());

    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenGetCustRole() {
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);
        when(bookingRepository.getOne(any())).thenReturn(booking);
        service.get("user2", "custrole",UUID.randomUUID().toString(),UUID.randomUUID().toString());

    }

    @Test
    public void shouldGetBookingListEmpty() {
        restaurant.setBookings(null);
        when(restaurantRepository.getOne(any())).thenReturn(restaurant);

        List<BookingModel> response = service.getList("admin", "adminrole",UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        assertThat(response).isEmpty();
    }


}
