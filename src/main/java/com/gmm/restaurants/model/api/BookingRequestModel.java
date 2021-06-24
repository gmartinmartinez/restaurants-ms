package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * Model to create a booking
 */
@Validated
@Data
public class BookingRequestModel   {
  @JsonProperty("bookingDate")
  @NotNull
  private LocalDateTime bookingDate;

  @JsonProperty("numberOfPeople")
  @NotNull
  @Min(1)
  @Max(50)
  private Integer numberOfPeople;

  @JsonProperty("name")
  @NotNull
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  @Size(max = 100)
  private String name;

  @JsonProperty("phone")
  @NotNull
  @Pattern(regexp="^[0-9]*$")
  @Size(min = 9, max = 13)
  private String phone;

  @Email
  @Size(max = 100)
  @JsonProperty("email")
  private String email;

  @JsonProperty("comments")
  @Size(max = 500)
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String comments;

}
