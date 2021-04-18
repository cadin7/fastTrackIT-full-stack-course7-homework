package ro.fasttrackit.homework7.restaurant.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.fasttrackit.homework7.restaurant.server.domain.Restaurant;
import ro.fasttrackit.homework7.restaurant.server.repository.RestaurantRepository;

import java.util.List;

import static java.time.LocalDate.now;

@SpringBootApplication
public class RestaurantServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServerApplication.class, args);
    }

    @Bean
    CommandLineRunner atStartup(RestaurantRepository repository){
        return args -> repository.saveAll(List.of(
                Restaurant.builder()
                        .name("Restaurant - 1")
                        .city("Oradea")
                        .since(now().minusYears(1))
                        .stars(4)
                        .build(),
                Restaurant.builder()
                        .name("Restaurant - 2")
                        .city("Oradea")
                        .since(now().minusYears(2))
                        .stars(5)
                        .build(),
                Restaurant.builder()
                        .name("Restaurant - 3")
                        .city("Bors")
                        .since(now().minusYears(3))
                        .stars(2)
                        .build(),
                Restaurant.builder()
                        .name("Restaurant - 4")
                        .city("Oradea")
                        .since(now().minusYears(4))
                        .stars(1)
                        .build(),
                Restaurant.builder()
                        .name("Restaurant - 1")
                        .city("Iasi")
                        .since(now().minusYears(5))
                        .stars(4)
                        .build()));
    }
}
