package rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class AppTest {

    private static final String URL = "https://freegeoip.app/json";
    private static final String RESULT_JSON = "{\"ip\":\"666\",\"country_code\":\"ZW\",\"country_name\":\"Зимбабве\",\"region_code\":\"\",\"region_name\":\"\",\"city\":\"Harare\",\"zip_code\":\"\",\"time_zone\":\"UTC+2:00\",\"latitude\":0,\"longitude\":0,\"metro_code\":0}";
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    @Test
    public void testForCorrectData() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo(URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(RESULT_JSON));

        LocationDto loc = restTemplate.getForObject(URL, LocationDto.class);
        Assertions.assertNotNull(loc);
        Assertions.assertEquals(loc.getCountry(), "Зимбабве");
        Assertions.assertEquals(loc.getRegion(), "");
        Assertions.assertEquals(loc.getCity(), "Harare");
        Assertions.assertEquals(loc.getLatitude(), 0.0);
        Assertions.assertEquals(loc.getLongitude(), 0.0);
    }
}
