package ro.fasttrackit.homework7.restaurant.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ro.fasttrackit.homework7.restaurant.server.domain.City;
import ro.fasttrackit.homework7.restaurant.server.domain.Restaurant;
import ro.fasttrackit.homework7.restaurant.server.exceptions.ValidationException;
import ro.fasttrackit.homework7.restaurant.server.repository.RestaurantRepository;

import java.util.Arrays;
import java.util.Optional;

import static java.time.LocalDate.now;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Component
@RequiredArgsConstructor
public class RestaurantValidator {
    private final RestaurantRepository repository;
    private final Environment environment;
    private final City city;

    public void validateNewThrow(Restaurant restaurant) {
        validate(restaurant)
                .ifPresent(e -> {
                    throw e;
                });
    }

    private Optional<ValidationException> validate(Restaurant restaurant) {
        if (restaurant.getName() == null) {
            return of(new ValidationException("Restaurant name cannot be NULL!"));
        } else if (!city.getCities().contains(restaurant.getCity())) {
            return of(new ValidationException("Restaurant city must be from: "
                    + Arrays.toString(environment.getActiveProfiles())));
        } else if (repository.existsByName(restaurant.getName())) {
            return of(new ValidationException("Restaurant name cannot be duplicated!"));
        } else if (now().isBefore(restaurant.getSince())) {
            return of(new ValidationException("Restaurant since date must be older than current date!"));
        } else {
            return empty();
        }
    }
}
