package com.climatecrop.backend.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String API_KEY = "75057ce8581a7e0fe21fe8978b426680";

    public Map<String, Object> getWeather(String city) {

        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q="
                    + city + "&appid=" + API_KEY + "&units=metric";

            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.getForObject(url, Map.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}