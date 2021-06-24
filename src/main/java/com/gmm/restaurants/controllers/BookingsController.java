package com.gmm.restaurants.controllers;

import com.gmm.restaurants.configuration.SwaggerUiConfiguration;
import com.gmm.restaurants.model.api.BookingModel;
import com.gmm.restaurants.model.api.BookingRequestModel;
import com.gmm.restaurants.model.api.ErrorResponse;
import com.gmm.restaurants.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
@RequestMapping(value = SwaggerUiConfiguration.BASE_PATH + "/restaurants")
public class BookingsController {

    private final BookingService service;

    public BookingsController(BookingService service) {
        this.service = service;
    }


    @Operation(summary = "Delete restaurant booking", description = "Delete a restaurant booking given its identifier ", tags={ "BOOKINGS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Success"),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @DeleteMapping(value = "/{restaurantId}/bookings/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") Integer restaurantId,@Parameter(in = ParameterIn.PATH, description = "The booking identifier.", required=true, schema=@Schema()) @PathVariable("bookingId") Integer bookingId) {
        log.info("Delete restaurant booking");
        service.delete(restaurantId, bookingId);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Operation(summary = "Get booking by id", description = "Get a booking by identifier for the restaurant", tags={ "BOOKINGS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingModel.class))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(value = "/{restaurantId}/bookings/{bookingId}", produces = { "application/json" })
    public ResponseEntity<BookingModel> getBookingById(@Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") Integer restaurantId,@Parameter(in = ParameterIn.PATH, description = "The booking identifier.", required=true, schema=@Schema()) @PathVariable("bookingId") Integer bookingId) {
        BookingModel booking = service.get(restaurantId, bookingId);
        if(booking!=null)
            return new ResponseEntity<BookingModel>(booking, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Get bookings of the restaurant", description = "Get booking list for the restaurant", tags={ "BOOKINGS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookingModel.class)))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(value = "/{restaurantId}/bookings'", produces = { "application/json" })
    public ResponseEntity<List<BookingModel>> getRestaurantBookings(@Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") Integer restaurantId) {
        log.info("Get restaurant bookings list");
        return new ResponseEntity<List<BookingModel>>(service.getList(restaurantId), HttpStatus.OK);
    }

    @Operation(summary = "Create booking", description = "Create a new booking for the restaurant", tags={ "BOOKINGS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingModel.class))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @PostMapping(value = "/{restaurantId}/bookings'", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<BookingModel> createRestaurantBooking(@Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") Integer restaurantId,@Parameter(in = ParameterIn.DEFAULT, description = "The information of the booking.", schema=@Schema()) @Valid @RequestBody BookingRequestModel body) {
        log.info("Create restaurant booking");
        return new ResponseEntity<BookingModel>(service.create(restaurantId, body), HttpStatus.OK);
    }

}
