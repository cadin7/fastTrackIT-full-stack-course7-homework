package ro.fasttrackit.homework7.restaurant.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.homework7.restaurant.server.domain.Restaurant;
import ro.fasttrackit.homework7.restaurant.server.exceptions.ResourceNotFoundException;
import ro.fasttrackit.homework7.restaurant.server.model.RestaurantFilters;
import ro.fasttrackit.homework7.restaurant.server.repository.RestaurantRepository;

import java.util.Optional;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository repository;
    private final RestaurantValidator validator;
    private final ObjectMapper mapper;

    public Page<Restaurant> getAll(RestaurantFilters filters, Pageable pageable) {
        if (!isEmpty(filters.getStars()) && filters.getCity() != null) {
            return repository.findByStarsInAndCityIgnoreCase(filters.getStars(), filters.getCity(), pageable);
        } else if (!isEmpty(filters.getStars())) {
            return repository.findByStarsIn(filters.getStars(), pageable);
        } else if (filters.getCity() != null) {
            return repository.findByCityIgnoreCase(filters.getCity(), pageable);
        } else {
            return repository.findAll(pageable);
        }
    }

    public Optional<Restaurant> getRestaurant(Long restaurantId) {
        return repository.findById(restaurantId);
    }

    public Restaurant addRestaurant(Restaurant newRestaurant) {
        validator.validateNewThrow(newRestaurant);

        return repository.save(newRestaurant);
    }

    public Restaurant replaceRestaurant(Long restaurantId, Restaurant newRestaurant) {
        final var dbRestaurant = getRestaurantOrElseThrow(restaurantId);
        validator.validateNewThrow(newRestaurant);
        newRestaurant.setId(restaurantId);
        newRestaurant.setSince(dbRestaurant.getSince());

        return repository.save(newRestaurant);
    }

    private Restaurant getRestaurantOrElseThrow(Long restaurantId) {
        return repository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find " + "restaurant with ID: " + restaurantId));
    }

    @SneakyThrows
    public Restaurant patchRestaurant(Long restaurantId, JsonPatch patch) {
        final var dbRestaurant = getRestaurantOrElseThrow(restaurantId);
        JsonNode patchedJson = patch.apply(mapper.valueToTree(dbRestaurant));
        Restaurant patchedRestaurant = mapper.treeToValue(patchedJson, Restaurant.class);

        return replaceRestaurant(restaurantId, patchedRestaurant);
    }

    public Optional<Restaurant> deleteRestaurant(Long restaurantId) {
        var restaurantToDelete = repository.findById(restaurantId);
        repository.deleteById(restaurantId);

        return restaurantToDelete;
    }
}
