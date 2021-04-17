package ro.fasttrackit.homework7.restaurant.server.domain;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("romania")
public class Romania implements City {
    @Override
    public List<String> getCities() {
        return List.of(
                "Oradea",
                "Cluj",
                "Bors",
                "Csikszereda",
                "Iasi",
                "Brasov",
                "Timisoara",
                "Arad"
        );
    }
}
