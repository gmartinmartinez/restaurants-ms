package com.gmm.restaurants.repositories;

import com.gmm.restaurants.model.entities.RestaurantEntity;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, String> {

}
