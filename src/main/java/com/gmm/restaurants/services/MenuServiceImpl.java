package com.gmm.restaurants.services;

import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.DishModel;
import com.gmm.restaurants.model.api.MenuModel;
import com.gmm.restaurants.model.api.SummaryMenuModel;
import com.gmm.restaurants.model.entities.DishEntity;
import com.gmm.restaurants.model.entities.MenuEntity;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.repositories.MenuRepository;
import com.gmm.restaurants.repositories.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService{

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public MenuServiceImpl (RestaurantRepository restaurantRepository, MenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public List<SummaryMenuModel> getMenuList(String restaurantId) {
        try {
            RestaurantEntity entity = restaurantRepository.getOne(restaurantId);
            if(entity.getMenus()!=null ) {
                return entity.getMenus().stream().map(this::mapEntityToSummaryMenuModel).collect(Collectors.toList());
            }
            return new ArrayList<>();
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("restaurant", restaurantId);
        }
    }

    @Override
    public MenuModel getMenuDishes(String restaurantId, String menuId) {
        try {
            restaurantRepository.getOne(restaurantId).getAddress();
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("restaurant", restaurantId);
        }

        try {
            return mapEntityToMenuModel(menuRepository.getOne(menuId));
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("menu", menuId);
        }

    }

    private SummaryMenuModel mapEntityToSummaryMenuModel(MenuEntity entity){
            return SummaryMenuModel.builder()
                .id(entity.getId())
                .availability(entity.getAvailability())
                .description(entity.getDescription())
                .build();
    }

    private MenuModel mapEntityToMenuModel(MenuEntity entity){
            return MenuModel.builder()
                .id(entity.getId())
                .availability(entity.getAvailability())
                .description(entity.getDescription())
                .dishes(mapDishesList(entity.getDishes()))
                .build();
    }

    private List<DishModel> mapDishesList(List<DishEntity> dishes) {
        if (dishes!=null) {
           return dishes.stream().map(d -> DishModel.builder()
                                        .id(d.getId())
                                        .description(d.getDescription())
                                        .isVegetarian(d.getIsVegetarian())
                                        .build()
                ).collect(Collectors.toList());
        }
        return new ArrayList<>();

    }
}
