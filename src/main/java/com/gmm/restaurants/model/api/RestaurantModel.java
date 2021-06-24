package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * RestaurantModel
 */
@Validated
@Data
@Builder
public class RestaurantModel   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("address")
  private String address;

  @JsonProperty("zipcode")
  private String zipcode;

  @JsonProperty("city")
  private String city;

  @JsonProperty("province")
  private String province;

  @JsonProperty("country")
  private String country;

  @JsonProperty("email")
  private String email;

  @JsonProperty("website")
  private String website;

  @JsonProperty("phone")
  private String phone;

  @JsonProperty("description")
  private String description;

  @JsonProperty("foodType")
  private String foodType;

  @JsonProperty("averageAmountPerPerson")
  private Double averageAmountPerPerson;

}
