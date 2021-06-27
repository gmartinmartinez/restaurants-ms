package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * MenuModel
 */
@Validated
@Data
@Builder
public class MenuModel   {
  @JsonProperty("id")
  private String id;

  @JsonProperty("description")
  private String description;

  @JsonProperty("availability")
  private String availability;

  @JsonProperty("dishes")
  @Valid
  private List<DishModel> dishes;

}
