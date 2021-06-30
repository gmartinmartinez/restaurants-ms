package com.gmm.restaurants.services;

import com.gmm.restaurants.exceptions.ForbiddenResourceException;
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
import java.util.UUID;
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
    public BookingModel get(String userName, String userRole, String restaurantId, String bookingId) {
     try {
            restaurantRepository.getOne(restaurantId).getAddress();
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("restaurant", restaurantId);
        }

        try {
            BookingEntity entity = bookingRepository.getOne(bookingId);
            if ((userName.equals(entity.getUser()) && userRole.equals("custrole")) ||
                userRole.equals("adminrole") || userRole.equals("restrole") )  {
                return mapEntityToModel(entity);
            } else {
                throw new ForbiddenResourceException("booking", userName);
            }

        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("booking", bookingId);
        }
    }

    @Override
    public List<BookingModel> getList(String userName, String userRole, String restaurantId) {
        try {
            RestaurantEntity entity = restaurantRepository.getOne(restaurantId);
            if ((userName.equals(entity.getUser()) && userRole.equals("restrole") ) || userRole.equals("adminrole") ) {
                if (entity.getBookings() != null) {
                    return entity.getBookings().stream().map(this::mapEntityToModel)
                        .collect(Collectors.toList());
                }
                return new ArrayList<>();
            } else {
                throw new ForbiddenResourceException("booking", userName);
            }
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("restaurant", restaurantId);
        }
    }

    @Override
    public BookingModel create(String userName, String userRole, String restaurantId, BookingRequestModel request) {
        try {
            RestaurantEntity entity = restaurantRepository.getOne(restaurantId);
            if ((userName.equals(entity.getUser()) && userRole.equals("restrole")) || userRole.equals("adminrole")
                            || userRole.equals("custrole")) {
                return mapEntityToModel(bookingRepository.saveAndFlush(mapRequestToEntity(userName, userRole, request, restaurantId)));
            } else {
                throw new ForbiddenResourceException("booking", userName);
            }
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("restaurant", restaurantId);
        }
    }

    @Override
    public void delete(String userName, String userRole, String restaurantId, String bookingId) {
        try {
            RestaurantEntity entity = restaurantRepository.getOne(restaurantId);
            if(!userName.equals(entity.getUser()) && userRole.equals("restrole")) {
                throw new ForbiddenResourceException("booking", userName);
            }
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("restaurant", restaurantId);
        }
        try {
            BookingEntity bookingEntity = bookingRepository.getOne(bookingId);
            if ((userName.equals(bookingEntity.getUser()) && userRole.equals("custrole")) ||
            userRole.equals("adminrole") || userRole.equals("restrole")) {
                bookingRepository.deleteById(bookingId);
            } else {
                throw new ForbiddenResourceException("booking", userName);
            }
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("booking", bookingId);
        }
    }

    private BookingModel mapEntityToModel(BookingEntity entity){
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

    private BookingEntity mapRequestToEntity(String userName, String userRole, BookingRequestModel request, String id){
        return BookingEntity.builder()
            .id(UUID.randomUUID().toString())
            .bookingDate(request.getBookingDate())
            .comments(request.getComments())
            .restaurant(id)
            .creationDate(LocalDateTime.now())
            .email(request.getEmail())
            .name(request.getName())
            .peopleNumber(request.getNumberOfPeople())
            .phone(request.getPhone())
            .user(userName)
            .role(userRole)
            .build();
    }
}
