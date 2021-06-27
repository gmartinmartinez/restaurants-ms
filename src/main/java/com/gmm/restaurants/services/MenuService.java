package com.gmm.restaurants.services;

import com.gmm.restaurants.model.api.MenuModel;
import com.gmm.restaurants.model.api.SummaryMenuModel;
import java.util.List;

public interface MenuService {

    List<SummaryMenuModel> getMenuList(String restaurantId);

    MenuModel getMenuDishes(String restaurantId, String menuId);

}
