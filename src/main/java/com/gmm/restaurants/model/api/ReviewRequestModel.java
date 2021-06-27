package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

/**
 * ReviewRequestModel
 */
@Validated
@Data
@Builder
public class ReviewRequestModel   {
  @JsonProperty("senderNick")
  @NotNull
  @Size(max = 50)
  @Pattern(regexp="\"^[a-zA-Z0-9]*$")
  private String senderNick;

  /**
   * The given stars from 1 (bad) to 5 (great).
   */
  @Getter
  public enum StarsEnum {
    NUMBER_1(1),
    
    NUMBER_2(2),
    
    NUMBER_3(3),
    
    NUMBER_4(4),
    
    NUMBER_5(5);

    private Integer value;

    StarsEnum(Integer value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StarsEnum fromValue(Integer text) {
      for (StarsEnum b : StarsEnum.values()) {
        if (b.value==text) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("stars")
  @NotNull
  private StarsEnum stars;

  @JsonProperty("title")
  @Size(max = 150)
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String title;

  @JsonProperty("description")
  @Size(max = 500)
  @Pattern(regexp="^[\\.a-zA-Z0-9,!? ]*$")
  private String description;

}
