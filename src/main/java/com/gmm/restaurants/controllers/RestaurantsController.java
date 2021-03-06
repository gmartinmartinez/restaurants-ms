package com.gmm.restaurants.controllers;

import com.gmm.restaurants.configuration.SwaggerUiConfiguration;
import com.gmm.restaurants.model.api.ErrorResponse;
import com.gmm.restaurants.model.api.RestaurantModel;
import com.gmm.restaurants.model.api.RestaurantRequestModel;
import com.gmm.restaurants.services.RestaurantService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
@CrossOrigin(origins = "*")
@RequestMapping(value = SwaggerUiConfiguration.BASE_PATH + "/restaurants")
public class RestaurantsController{

    private final RestaurantService service;

    public RestaurantsController(RestaurantService service) {
        this.service = service;
    }

    @Operation(summary = "Get restaurant list", description = "Retrieve the list of existing restaurants ", tags={ "RESTAURANTS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Partial Content", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RestaurantModel.class)))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(produces = { "application/json" })
    public ResponseEntity<List<RestaurantModel>> getRestaurants() {
        log.info("Get restaurant list");
        return new ResponseEntity<List<RestaurantModel>>(service.getList(), HttpStatus.OK);
    }

    @Operation(summary = "Create restaurant", description = "Create a new restaurant", tags={ "RESTAURANTS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantModel.class))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @PostMapping(produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<RestaurantModel> createRestaurant(
        @RequestHeader(name = "x-username") @NotNull String userName,
        @RequestHeader(name = "x-role") @NotNull String userRole,
        @Parameter(in = ParameterIn.DEFAULT, description = "The information of the restaurant.", schema=@Schema()) @Valid @RequestBody RestaurantRequestModel body) {
        log.info("Create restaurant");
        return new ResponseEntity<RestaurantModel>(service.create(userName, userRole, body), HttpStatus.CREATED);
    }


    @Operation(summary = "Delete restaurant", description = "Delete a restaurant given its identifier ", tags={ "RESTAURANTS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Success"),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @DeleteMapping(value = "/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(
        @RequestHeader(name = "x-username") @NotNull String userName,
        @RequestHeader(name = "x-role") @NotNull String userRole,
        @Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") UUID restaurantId) {
        log.info("Delete restaurant");
        service.delete(userName, userRole, restaurantId.toString());
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get restaurant", description = "Get restaurant by Id", tags={ "RESTAURANTS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantModel.class))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(value = "/{restaurantId}", produces = { "application/json" })
    public ResponseEntity<RestaurantModel> getRestaurantById(@Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") UUID restaurantId) {
        return new ResponseEntity<RestaurantModel>(service.get(restaurantId.toString()), HttpStatus.OK);
    }



}
