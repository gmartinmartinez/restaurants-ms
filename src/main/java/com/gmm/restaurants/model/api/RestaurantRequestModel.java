package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * RestaurantRequestModel
 */
@Validated
@Data
public class RestaurantRequestModel   {
  @JsonProperty("name")
  @NotNull
  @Size(min = 1, max = 100)
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String name;

  @JsonProperty("address")
  @NotNull
  @Size(min = 1, max = 100)
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String address;

  @Size(min = 5, max = 5)
  @JsonProperty("zipcode")
  @Pattern(regexp="^[0-9]*$")
  private String zipcode;

  @JsonProperty("city")
  @NotNull
  @Size(max = 50)
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String city;

  @JsonProperty("province")
  @Size(max = 50)
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String province;

  @JsonProperty("country")
  @Size(max = 50)
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String country;

  @JsonProperty("email")
  @Size(max = 100)
  @Email
  private String email;

  @JsonProperty("website")
  @Size(max = 120)
  private String website;

  @Size(min = 9, max = 13)
  @JsonProperty("phone")
  @NotNull
  @Pattern(regexp="^[0-9]*$")
  private String phone;

  @Size(max = 200)
  @JsonProperty("description")
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String description;

  @Size(max = 50)
  @JsonProperty("foodType")
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String foodType;

  @JsonProperty("averageAmountPerPerson")
  private Double averageAmountPerPerson;

}
