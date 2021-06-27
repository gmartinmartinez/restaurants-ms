package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * DishModel
 */
@Validated
@Data
@Builder
public class DishModel   {
  @JsonProperty("id")
  private String id;

  @JsonProperty("description")
  private String description;

  @JsonProperty("isVegetarian")
  private Boolean isVegetarian;

}
