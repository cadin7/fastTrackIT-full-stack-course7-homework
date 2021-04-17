package ro.fasttrackit.homework7.restaurant.server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.fasttrackit.homework7.restaurant.server.domain.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByName(String name);

    Page<Restaurant> findByCityIgnoreCase(String city, Pageable pageable);

    Page<Restaurant> findByStarsIn(List<Integer> stars, Pageable pageable);

    Page<Restaurant> findByStarsInAndCityIgnoreCase(List<Integer> stars, String city, Pageable pageable);
}
