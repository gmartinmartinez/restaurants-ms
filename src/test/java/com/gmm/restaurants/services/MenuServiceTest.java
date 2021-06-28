package com.gmm.restaurants.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.MenuModel;
import com.gmm.restaurants.model.api.SummaryMenuModel;
import com.gmm.restaurants.model.entities.MenuEntity;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.repositories.MenuRepository;
import com.gmm.restaurants.repositories.RestaurantRepository;
import com.gmm.restaurants.utils.EntityGeneratorUtils;
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
public class MenuServiceTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private final RestaurantEntity restaurant = EntityGeneratorUtils.createRestaurant();


    @Mock
    private RestaurantRepository repository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuServiceImpl service;

    @Before
    public void setUp() {
        this.service = new MenuServiceImpl(repository, menuRepository);
    }


    @Test
    public void shouldGetRestaurantMenus() {
        when(repository.getOne(any())).thenReturn(restaurant);

        List<SummaryMenuModel> response = service.getMenuList(UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        if(restaurant.getMenus()!=null)
            assertThat(response.size()).isEqualTo(restaurant.getMenus().size());
        else
            assertThat(response.size()).isEqualTo(0);

    }


    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetMenus() {
        when(repository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.getMenuList(UUID.randomUUID().toString());

    }


    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetMenuById() {
        when(repository.getOne(any())).thenReturn(restaurant);
        when(menuRepository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.getMenuDishes(UUID.randomUUID().toString(), UUID.randomUUID().toString());

    }

    @Test(expected = NotFoundException.class)
    public void shouldGetNotFoundExceptionWhenGetRestaurantById() {
        when(repository.getOne(any())).thenThrow(new EntityNotFoundException());
        service.getMenuDishes(UUID.randomUUID().toString(), UUID.randomUUID().toString());

    }

    @Test
    public void shouldGetRestaurantMenuById() {
        String id = UUID.randomUUID().toString();
        MenuEntity mockData = EntityGeneratorUtils.createMenu();
        when(repository.getOne(any())).thenReturn(restaurant);
        when(menuRepository.getOne(any())).thenReturn(mockData);


        MenuModel response = service.getMenuDishes(id, id);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getDescription()).isEqualTo(mockData.getDescription());
        assertThat(response.getAvailability()).isEqualTo(mockData.getAvailability());
        assertThat(response.getDishes().size()).isEqualTo(mockData.getDishes().size());

    }

    @Test
    public void shouldGetRestaurantMenusEmpty() {
        restaurant.setMenus(null);
        when(repository.getOne(any())).thenReturn(restaurant);

        List<SummaryMenuModel> response = service.getMenuList(UUID.randomUUID().toString());
        assertThat(response).isNotNull();
        assertThat(response).isEmpty();

    }

    @Test
    public void shouldGetRestaurantMenuByIdEmptyDishes() {
        String id = UUID.randomUUID().toString();
        MenuEntity mockData = EntityGeneratorUtils.createMenu();
        mockData.setDishes(null);
        when(repository.getOne(any())).thenReturn(restaurant);
        when(menuRepository.getOne(any())).thenReturn(mockData);


        MenuModel response = service.getMenuDishes(id, id);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(mockData.getId());
        assertThat(response.getDescription()).isEqualTo(mockData.getDescription());
        assertThat(response.getAvailability()).isEqualTo(mockData.getAvailability());
        assertThat(response.getDishes()).isEmpty();

    }


}
