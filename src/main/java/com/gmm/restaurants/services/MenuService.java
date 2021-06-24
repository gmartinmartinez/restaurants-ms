package com.gmm.restaurants.services;

import com.gmm.restaurants.model.api.MenuModel;
import com.gmm.restaurants.model.api.SummaryMenuModel;
import java.util.List;

public interface MenuService {

    List<SummaryMenuModel> getMenuList(Integer restaurantId);

    MenuModel getMenuDishes(Integer restaurantId, Integer menuId);

}
