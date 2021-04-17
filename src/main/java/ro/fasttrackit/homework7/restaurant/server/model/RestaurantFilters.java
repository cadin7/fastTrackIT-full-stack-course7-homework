package ro.fasttrackit.homework7.restaurant.server.model;

import lombok.Value;

import java.util.List;

@Value
public class RestaurantFilters {
    List<Integer> stars;
    String city;
}
