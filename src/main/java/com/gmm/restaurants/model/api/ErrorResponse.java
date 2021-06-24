package com.gmm.restaurants.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

/**
 * ErrorResponse
 */
@Validated
@Data
@Builder
public class ErrorResponse   {
  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  /**
   * Gets or Sets severity
   */
  @Getter
  public enum SeverityEnum {
    CRITICAL("critical"),

    ERROR("error"),

    WARNING("warning"),

    INFO("info");

    private String value;

    SeverityEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static SeverityEnum fromValue(String text) {
      for (SeverityEnum b : SeverityEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("severity")
  private SeverityEnum severity;


}
