package com.climatecrop.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.climatecrop.backend.model.InputData;
import com.climatecrop.backend.model.RecommendationResponse;
import com.climatecrop.backend.service.CropService;
import com.climatecrop.backend.service.WeatherService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CropController {

    private final CropService cropService;
    private final WeatherService weatherService;

    public CropController(CropService cropService, WeatherService weatherService) {
        this.cropService = cropService;
        this.weatherService = weatherService;
    }

    @PostMapping("/recommend")
    public RecommendationResponse recommend(@RequestBody InputData input) {

        try {
            Map<String, Object> weather = weatherService.getWeather(input.getLocation());

            if (weather == null || weather.get("main") == null) {
                return errorResponse();
            }

            Map<String, Object> main = (Map<String, Object>) weather.get("main");

            float temp = ((Number) main.getOrDefault("temp", 0)).floatValue();
            float humidity = ((Number) main.getOrDefault("humidity", 0)).floatValue();

            String condition = "Clear";

            if (weather.get("weather") != null) {
                List<Map<String, Object>> list =
                        (List<Map<String, Object>>) weather.get("weather");

                if (!list.isEmpty()) {
                    condition = list.get(0).get("main").toString();
                }
            }

            float rain = 0;

            if (weather.get("rain") instanceof Map) {
                Map<String, Object> rainMap = (Map<String, Object>) weather.get("rain");

                Object val = rainMap.get("1h");
                if (val instanceof Number) {
                    rain = ((Number) val).floatValue();
                }
            }

            String crop = cropService.recommendCrop(
                    input.getN(), input.getP(), input.getK(),
                    temp, humidity, rain
            );

            String fertilizer = cropService.getFertilizer(
                    input.getN(), input.getP(), input.getK()
            );

            String irrigation = cropService.getIrrigation(
                    rain, humidity, condition, temp
            );

            return new RecommendationResponse(
                    crop, fertilizer, irrigation,
                    temp, humidity, rain, condition
            );

        } catch (Exception e) {
            e.printStackTrace();
            return errorResponse();
        }
    }

    private RecommendationResponse errorResponse() {
        return new RecommendationResponse(
                "N/A",
                "N/A",
                "Please wait for some time or check location",
                0, 0, 0,
                "Error"
        );
    }
}