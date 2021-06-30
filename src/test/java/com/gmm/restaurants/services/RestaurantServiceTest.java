package com.gmm.restaurants.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gmm.restaurants.exceptions.ForbiddenResourceException;
import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.repositories.RestaurantRepository;
import com.gmm.restaurants.utils.EntityGeneratorUtils;
import java.util.Arrays;
import java.util.List;
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
public class RestaurantServiceTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private final RestaurantEntity restaurant = EntityGeneratorUtils.createRestaurant();
    private final RestaurantRequestModel request = factory.manufacturePojo(RestaurantRequestModel.class);


    @Mock
    private RestaurantRepository repository;

    @InjectMocks
    private RestaurantServiceImpl service;

    @Before
    public void setUp() {
        this.service = new RestaurantServiceImpl(repository);
    }

    @Test
    public void shouldGetRestaurantList() {
        when(repository.findAll()).thenReturn(Arrays.asList(restaurant));

        List<RestaurantModel> response = service.getList();
        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(1);
    }

    @Test
    public void shouldGetRestaurantListEmpty() {
        when(repository.findAll()).thenReturn(null);

        List<RestaurantModel> response = service.getList();
        assertThat(response).isNotNull();
        assertThat(response).isEmpty();
    }

    @Test
    public void shouldGetRestaurantById() {
        RestaurantEntity mockData = restaurant;
        when(repository.getOne(any())).thenReturn(mockData);

        RestaurantModel response = service.get(UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getAddress()).isEqualTo(mockData.getAddress());
        assertThat(response.getDescription()).isEqualTo(mockData.getDescription());

    }

    @Test
    public void shouldCreateRestaurant() {
        RestaurantEntity mockData = restaurant;
        when(repository.saveAndFlush(any())).thenReturn(mockData);

        RestaurantModel response = service.create("admin", "adminrole", request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getAddress()).isEqualTo(mockData.getAddress());
        assertThat(response.getDescription()).isEqualTo(mockData.getDescription());
    }

    @Test
    public void shouldCreateRestaurantRestRole() {
        RestaurantEntity mockData = restaurant;
        when(repository.saveAndFlush(any())).thenReturn(mockData);

        RestaurantModel response = service.create("user", "restrole", request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getAddress()).isEqualTo(mockData.getAddress());
        assertThat(response.getDescription()).isEqualTo(mockData.getDescription());
    }

    @Test
    public void shouldDeleteRestaurant() {
        when(repository.getOne(any())).thenReturn(restaurant);

        String id = UUID.randomUUID().toString();
        service.delete("admin", "adminrole", id);

        verify(repository, times(1)).getOne(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void shouldDeleteRestaurantRestRole() {
        when(repository.getOne(any())).thenReturn(restaurant);

        String id = UUID.randomUUID().toString();
        service.delete("user", "restrole", id);

        verify(repository, times(1)).getOne(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetById() {
        when(repository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.get(UUID.randomUUID().toString());

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenDeleteById() {
        when(repository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.delete("admin", "adminrole",UUID.randomUUID().toString());
    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenDelete() {
        when(repository.getOne(any())).thenReturn(restaurant);
        service.delete("user2", "custrole",UUID.randomUUID().toString());

    }

    @Test(expected = ForbiddenResourceException.class)
    public void shouldGetForbiddenResourceExceptionWhenCreate() {
        service.create("user2", "custrole", this.request);

    }

}
