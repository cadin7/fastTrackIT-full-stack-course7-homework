package ro.fasttrackit.homework7.restaurant.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import ro.fasttrackit.homework7.restaurant.server.domain.Restaurant;
import ro.fasttrackit.homework7.restaurant.server.model.CollectionResponse;

import java.util.Random;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@ActiveProfiles
public class RestaurantIntegrationTest {
    private final static String BASE_URL = "http://localhost:8080/restaurants";

    @Test
    void testGetAll() {
        restTemplate().exchange(
                BASE_URL,
                GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<CollectionResponse<Restaurant>>() {
                }
        ).getBody()
                .getContent()
                .forEach(System.out::println);
    }

    @Test
    void testPostNew() {
        for (int i = 0; i < 5; i++) {
            var response = restTemplate().postForObject(
                    BASE_URL,
                    new Restaurant("Restaurant - " + i,
                            getRandomStars(),
                            "Oradea",
                            now().minusYears(i + 2)),
                    Restaurant.class);
            System.out.println(response);
        }
    }

    @Test
    void testPut() {
        var url = fromHttpUrl(BASE_URL)
                .path("/2")
                .toUriString();
        restTemplate().put(
                url,
                new Restaurant("new Restaurant",
                        getRandomStars(),
                        "Bors",
                        now().minusMonths(5)
                ));
    }

    @SneakyThrows
    @Test
    void testPatch() {
        String url = fromHttpUrl(BASE_URL)
                .path("/3")
                .toUriString();
        ObjectMapper mapper = new ObjectMapper();
        JsonPatch patch = JsonPatch.fromJson(mapper.readTree(
                """
                            [
                                 { "op": "replace", "path": "/name", "value": "patch Restaurant"},
                                 { "op": "replace", "path": "/stars", "value": 6 },
                                 { "op": "replace", "path": "/city", "value": "Iasi"}
                            ]
                        """
        ));
        Restaurant res = restTemplate().patchForObject(url, patch, Restaurant.class);
        Restaurant patchRes = new Restaurant("patch Restaurant", getRandomStars(), "Iasi", now().minusDays(5));
        JsonNode jsonNode = JsonDiff.asJson(mapper.valueToTree(res), mapper.valueToTree(patchRes));
        System.out.println(jsonNode);
    }

    @Test
    void testDelete() {
        var url = fromHttpUrl(BASE_URL)
                .path("/4")
                .toUriString();
        restTemplate().delete(url);
    }

    private RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    private int getRandomStars() {
        return new Random().nextInt(6);
    }
}
