package com.gmm.restaurants.repositories;

import com.gmm.restaurants.model.entities.MenuEntity;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface MenuRepository extends JpaRepository<MenuEntity, String> {
}
