package com.gmm.restaurants.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.MenuModel;
import com.gmm.restaurants.model.api.SummaryMenuModel;
import com.gmm.restaurants.services.MenuService;
import java.util.Arrays;
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
public class MenusControllerTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private final MenuModel menu = generateMenu();
    private final SummaryMenuModel summaryMenu = generateSummaryMenu();

    @Mock
    private MenuService service;

    @InjectMocks
    private MenusController controller;

    @Before
    public void setUp() {
        this.controller = new MenusController(service);
    }

    @Test
    public void shouldGetMenusListOK() {
        when(service.getMenuList(any())).thenReturn(Arrays.asList(summaryMenu));

        ResponseEntity<List<SummaryMenuModel>> response = controller.getRestaurantMenus(UUID.randomUUID());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
    }

    @Test(expected = NullPointerException.class)
    public void shouldGetExceptionWhenServiceFail() {
        when(service.getMenuList(any())).thenThrow(new NullPointerException());
         controller.getRestaurantMenus(UUID.randomUUID());
    }

    @Test
    public void shouldGetMenuByIdOK() {
        when(service.getMenuDishes(anyString(), anyString())).thenReturn(menu);

        ResponseEntity<MenuModel> response = controller.getRestaurantMenuById(UUID.randomUUID(), UUID.randomUUID());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldGetNotFoundMenuById() {
        when(service.getMenuDishes(anyString(), anyString())).thenReturn(null);

        ResponseEntity<MenuModel> response = controller.getRestaurantMenuById(UUID.randomUUID(), UUID.randomUUID());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test(expected = NullPointerException.class)
    public void shouldGetExceptionWhenServiceMenuFail() {
        when(service.getMenuDishes(anyString(), anyString())).thenThrow(new NullPointerException());
        controller.getRestaurantMenuById(UUID.randomUUID(), UUID.randomUUID());
    }

    private MenuModel generateMenu() {
        return factory.manufacturePojo(MenuModel.class);
    }

    private SummaryMenuModel generateSummaryMenu() {
        return factory.manufacturePojo(SummaryMenuModel.class);
    }

}
