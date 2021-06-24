package com.gmm.restaurants.controllers;

import com.gmm.restaurants.configuration.SwaggerUiConfiguration;
import com.gmm.restaurants.model.api.ErrorResponse;
import com.gmm.restaurants.model.api.MenuModel;
import com.gmm.restaurants.model.api.SummaryMenuModel;
import com.gmm.restaurants.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Validated
@RequestMapping(value = SwaggerUiConfiguration.BASE_PATH + "/restaurants")
public class MenusController {

    private final MenuService service;

    public MenusController(MenuService service) {
        this.service = service;
    }



    @Operation(summary = "Get menus of the restaurant", description = "Get menus list for the restaurant", tags={ "MENUS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SummaryMenuModel.class)))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(value = "/{restaurantId}/menus",
        produces = { "application/json" })
    public ResponseEntity<List<SummaryMenuModel>> getRestaurantMenus(@Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") Integer restaurantId) {
        log.info("Get restaurant menu list");
        return new ResponseEntity<List<SummaryMenuModel>>(service.getMenuList(restaurantId), HttpStatus.OK);

    }

    @Operation(summary = "Get a menu of the restaurant", description = "Get a menu by Id for the restaurant", tags={ "MENUS" })
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuModel.class))),

        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),

        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @GetMapping(value = "/{restaurantId}/menus/{menuId}",
        produces = { "application/json" })
    public ResponseEntity<MenuModel> getRestaurantMenuById(@Parameter(in = ParameterIn.PATH, description = "The restaurant identifier.", required=true, schema=@Schema()) @PathVariable("restaurantId") Integer restaurantId,@Parameter(in = ParameterIn.PATH, description = "The menu identifier.", required=true, schema=@Schema()) @PathVariable("menuId") Integer menuId) {
        log.info("Get restaurant menu detail");
        MenuModel menu = service.getMenuDishes(restaurantId, menuId);
        if(menu!=null)
            return new ResponseEntity<MenuModel>(menu, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
