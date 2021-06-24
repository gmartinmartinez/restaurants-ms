package com.gmm.restaurants.services;

import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.BookingModel;
import com.gmm.restaurants.model.api.BookingRequestModel;
import com.gmm.restaurants.model.entities.BookingEntity;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.repositories.BookingRepository;
import com.gmm.restaurants.repositories.RestaurantRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final RestaurantRepository restaurantRepository;
    private final BookingRepository bookingRepository;

    public BookingServiceImpl (RestaurantRepository restaurantRepository, BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public BookingModel get(Integer restaurantId, Integer bookingId) {
        return mapEntityToModel(bookingRepository.findByIdAndRestaurant(bookingId, restaurantId));
    }

    @Override
    public List<BookingModel> getList(Integer restaurantId) {
        try {
            RestaurantEntity entity = restaurantRepository.getOne(restaurantId);
            if(entity!=null && entity.getBookings()!=null ) {
                return entity.getBookings().stream().map(this::mapEntityToModel).collect(Collectors.toList());
            }
            return new ArrayList<>();
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw new NotFoundException("restaurant", restaurantId);
        }
    }

    @Override
    public BookingModel create(Integer restaurantId, BookingRequestModel request) {
        return mapEntityToModel(bookingRepository.saveAndFlush(mapRequestToEntity(request, restaurantId)));
    }

    @Override
    public void delete(Integer restaurantId, Integer bookingId) {
        if (bookingRepository.findByIdAndRestaurant(bookingId, restaurantId)!=null) {
            bookingRepository.deleteById(bookingId);
        } else {
            throw new NotFoundException("booking", bookingId);
        }
    }

    private BookingModel mapEntityToModel(BookingEntity entity){
        if(entity!=null){
            return BookingModel.builder()
                .id(entity.getId())
                .bookingDate(entity.getBookingDate())
                .creationDate(entity.getCreationDate())
                .comments(entity.getComments())
                .email(entity.getEmail())
                .name(entity.getName())
                .numberOfPeople(entity.getPeopleNumber())
                .phone(entity.getPhone())
                .build();
        }
        return null;
    }

    private BookingEntity mapRequestToEntity(BookingRequestModel request, Integer id){
        return BookingEntity.builder()
            .bookingDate(request.getBookingDate())
            .comments(request.getComments())
            .restaurant(id)
            .creationDate(LocalDateTime.now())
            .email(request.getEmail())
            .name(request.getName())
            .peopleNumber(request.getNumberOfPeople())
            .phone(request.getPhone())
            .build();
    }
}
