package rest;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class App {

    private static final String URL = "https://freegeoip.app/json";

    public static void main(String[] args) {
        RestTemplate rest = new RestTemplate();
        try {
            LocationDto myLoc = rest.getForObject(URL, LocationDto.class);
            System.out.println(myLoc);
        } catch (HttpClientErrorException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
