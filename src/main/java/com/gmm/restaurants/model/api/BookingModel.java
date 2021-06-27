package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * BookingModel
 */
@Data
@Validated
@Builder
public class BookingModel {

  @JsonProperty("id")
  private String id;

  @JsonProperty("creationDate")
  private LocalDateTime creationDate;

  @JsonProperty("bookingDate")
  private LocalDateTime bookingDate;

  @JsonProperty("numberOfPeople")
  private Integer numberOfPeople;

  @JsonProperty("name")
  private String name;

  @JsonProperty("phone")
  private String phone;

  @JsonProperty("email")
  private String email;

  @JsonProperty("comments")
  private String comments;

}