package ro.fasttrackit.homework7.restaurant.server.domain;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("hungary")
public class Hungary implements City {
    @Override
    public List<String> getCities() {
        return List.of(
                "Debrecen",
                "Budapest",
                "Pecs",
                "Nyirbator",
                "Gyor",
                "Bugyi",
                "Tata",
                "Totkomlos"
        );
    }
}
