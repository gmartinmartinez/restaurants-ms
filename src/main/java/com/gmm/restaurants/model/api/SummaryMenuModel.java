package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * SummaryMenuModel
 */
@Validated
@Data
@Builder
public class SummaryMenuModel   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("availability")
  private String availability = null;

}
