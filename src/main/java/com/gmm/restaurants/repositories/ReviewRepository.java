package com.gmm.restaurants.repositories;

import com.gmm.restaurants.model.entities.ReviewEntity;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {


}
