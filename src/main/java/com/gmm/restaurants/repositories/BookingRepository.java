package com.gmm.restaurants.repositories;

import com.gmm.restaurants.model.entities.BookingEntity;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface BookingRepository extends JpaRepository<BookingEntity, String> {


}
