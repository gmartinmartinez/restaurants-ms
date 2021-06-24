package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * ReviewModel
 */
@Validated
@Data
@Builder
public class ReviewModel   {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("creationDate")
  private LocalDateTime creationDate;

  @JsonProperty("senderNick")
  private String senderNick;

  @JsonProperty("stars")
  private Integer stars;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

}
