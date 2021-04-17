package ro.fasttrackit.homework7.restaurant.server.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.homework7.restaurant.server.domain.Restaurant;
import ro.fasttrackit.homework7.restaurant.server.exceptions.ResourceNotFoundException;
import ro.fasttrackit.homework7.restaurant.server.model.CollectionResponse;
import ro.fasttrackit.homework7.restaurant.server.model.PageInfo;
import ro.fasttrackit.homework7.restaurant.server.model.RestaurantFilters;
import ro.fasttrackit.homework7.restaurant.server.service.RestaurantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("restaurants")
public class RestaurantController {
    private final RestaurantService service;

    @GetMapping
    CollectionResponse<Restaurant> getAll(RestaurantFilters filters, Pageable pageable) {
        Page<Restaurant> productPage = service.getAll(filters, pageable);
        return CollectionResponse.<Restaurant>builder()
                .content(productPage.getContent())
                .pageInfo(PageInfo.builder()
                        .totalPages(productPage.getTotalPages())
                        .totalElements(productPage.getNumberOfElements())
                        .crtPage(pageable.getPageNumber())
                        .pageSize(pageable.getPageSize())
                        .build())
                .build();
    }

    @GetMapping("{restaurantId}")
    Restaurant getRestaurant(@PathVariable Long restaurantId) {
        return service.getRestaurant(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find restaurant with ID: " + restaurantId));
    }

    @PostMapping
    Restaurant addRestaurant(@RequestBody Restaurant newRestaurant) {
        return service.addRestaurant(newRestaurant);
    }

    @PutMapping("{restaurantId}")
    Restaurant replaceRestaurant(@PathVariable Long restaurantId, @RequestBody Restaurant newRestaurant) {
        return service.replaceRestaurant(restaurantId, newRestaurant);
    }

    @PatchMapping("{restaurantId}")
    Restaurant patchRestaurant(@PathVariable Long restaurantId, @RequestBody JsonPatch patch) {
        return service.patchRestaurant(restaurantId, patch);
    }

    @DeleteMapping("{restaurantId}")
    Restaurant deleteRestaurant(@PathVariable Long restaurantId) {
        return service.deleteRestaurant(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find restaurant with ID: " + restaurantId));
    }
}
