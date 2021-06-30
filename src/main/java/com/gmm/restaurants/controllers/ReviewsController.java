package com.gmm.restaurants.controllers;

import com.gmm.restaurants.configuration.SwaggerUiConfiguration;
import com.gmm.restaurants.model.api.ErrorResponse;
import com.gmm.restaurants.model.api.ReviewModel;
import com.gmm.restaurants.model.api.ReviewRequestModel;
import com.gmm.restaurants.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
@CrossOrigin(origins = "*")
@RequestMapping(value = SwaggerUiConfiguration.BASE_PATH + "/restaurants")
public class ReviewsController {

    private final ReviewService service;

    public ReviewsController(ReviewService service) {
        this.service = service;
    }


    @Operation(summary = "Get reviews", description = "Get a list of reviews for a restaurant", tags={ "REVIEWS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReviewModel.class)))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(value = "/{restaurantId}/reviews", produces = { "application/json" })
    public ResponseEntity<List<ReviewModel>> getRestaurantReviews(@Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") UUID restaurantId) {
        log.info("Get restaurant reviews list");
        return new ResponseEntity<List<ReviewModel>>(service.getList(restaurantId.toString()), HttpStatus.OK);
    }

    @Operation(summary = "Create review", description = "Create a restaurant review", tags={ "REVIEWS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewModel.class))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @PostMapping(value = "/{restaurantId}/reviews", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<ReviewModel> createRestaurantReview(
        @RequestHeader(name = "x-username") @NotNull String userName,
        @RequestHeader(name = "x-role") @NotNull String userRole,
        @Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") UUID restaurantId,@Parameter(in = ParameterIn.DEFAULT, description = "The information of the restaurant.", schema=@Schema()) @Valid @RequestBody ReviewRequestModel body) {
        log.info("Create restaurant review");
        return new ResponseEntity<ReviewModel>(service.create(userName, userRole, restaurantId.toString(), body), HttpStatus.CREATED);
    }

    @Operation(summary = "Get restaurant review", description = "Get restaurant review by Id", tags={ "REVIEWS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewModel.class))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(value = "/{restaurantId}/reviews/{reviewId}", produces = { "application/json" })
    public ResponseEntity<ReviewModel> getRestaurantReviewById(
        @Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") UUID restaurantId,@Parameter(in = ParameterIn.PATH, description = "The review identifier.", required=true, schema=@Schema()) @PathVariable("reviewId") UUID reviewId) {
         return new ResponseEntity<ReviewModel>(service.get(restaurantId.toString(), reviewId.toString()), HttpStatus.OK);
    }

    @Operation(summary = "Update restaurant review", description = "Update restaurant review by Id", tags={ "REVIEWS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewModel.class))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @PutMapping(value = "/{restaurantId}/reviews/{reviewId}", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<ReviewModel> updateRestaurantReview(
        @RequestHeader(name = "x-username") @NotNull String userName,
        @RequestHeader(name = "x-role") @NotNull String userRole,
        @Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") UUID restaurantId,@Parameter(in = ParameterIn.PATH, description = "The review identifier.", required=true, schema=@Schema()) @PathVariable("reviewId") UUID reviewId,@Parameter(in = ParameterIn.DEFAULT, description = "The information of the review.", schema=@Schema()) @Valid @RequestBody ReviewRequestModel body) {
        log.info("Update restaurant review");
        return new ResponseEntity<ReviewModel>(service.update(userName, userRole, restaurantId.toString(), reviewId.toString(), body), HttpStatus.OK);
    }


}
