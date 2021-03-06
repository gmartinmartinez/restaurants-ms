package com.gmm.restaurants.services;

import com.gmm.restaurants.exceptions.ForbiddenResourceException;
import com.gmm.restaurants.exceptions.NotFoundException;
import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import com.gmm.restaurants.model.entities.RestaurantEntity;
import com.gmm.restaurants.repositories.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }
    @Override
    public RestaurantModel get(String restaurantId) {
        try {
            return mapEntityToModel(repository.getOne(restaurantId));
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("restaurant", restaurantId);
        }
    }

    @Override
    public List<RestaurantModel> getList() {
        List<RestaurantEntity> list = repository.findAll();
        if(list!=null)
            return list.stream().map(this::mapEntityToModel).collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public RestaurantModel create(String userName, String userRole, RestaurantRequestModel restaurant) {
        if(userRole.equals("custrole") )
            throw new ForbiddenResourceException("booking", userName);
        return mapEntityToModel(repository.saveAndFlush(mapRequestToEntity(userName, userRole, restaurant)));
    }

    @Override
    public void delete(String userName, String userRole, String id) {
        try {
            RestaurantEntity entity = repository.getOne(id);
            if ((userName.equals(entity.getUser()) && userRole.equals("restrole") ) || userRole.equals("adminrole") )  {
                repository.deleteById(id);
            } else {
                throw new ForbiddenResourceException("restaurant", userName);
            }
        } catch (EntityNotFoundException e) {
            log.error("EntityNotFoundException", e);
            throw new NotFoundException("restaurant", id);
        }

    }

    private RestaurantModel mapEntityToModel(RestaurantEntity entity){
            return RestaurantModel.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .description(entity.getDescription())
                .averageAmountPerPerson(entity.getAverageAmount())
                .city(entity.getCity())
                .country(entity.getCountry())
                .email(entity.getEmail())
                .foodType(entity.getFoodType())
                .name(entity.getName())
                .phone(entity.getPhone())
                .province(entity.getProvince())
                .website(entity.getWebsite())
                .zipcode(entity.getZipcode())
                .build();
    }

    private RestaurantEntity mapRequestToEntity(String userName, String userRole, RestaurantRequestModel request){
            return RestaurantEntity.builder()
                .id(UUID.randomUUID().toString())
                .address(request.getAddress())
                .description(request.getDescription())
                .averageAmount(request.getAverageAmountPerPerson())
                .city(request.getCity())
                .country(request.getCountry())
                .email(request.getEmail())
                .foodType(request.getFoodType())
                .name(request.getName())
                .phone(request.getPhone())
                .province(request.getProvince())
                .website(request.getWebsite())
                .zipcode(request.getZipcode())
                .user(userName)
                .role(userRole)
                .build();
    }
}
