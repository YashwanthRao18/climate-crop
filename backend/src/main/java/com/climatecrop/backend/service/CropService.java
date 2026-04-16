package com.climatecrop.backend.service;

import org.springframework.stereotype.Service;

@Service
public class CropService {

    public String recommendCrop(int n, int p, int k, float temp, float humidity, float rain) {

        if (rain > 150 && temp > 25) return "Rice";
        if (temp < 25 && humidity > 50) return "Wheat";
        if (rain < 50 && temp > 30) return "Millet";
        return "Maize";
    }

    public String getFertilizer(int n, int p, int k) {

        StringBuilder result = new StringBuilder();

        if (n < 50) result.append("Fertilizer : Urea\n");
        if (p < 40) result.append("Fertilizer : DAP\n");
        if (k < 40) result.append("Fertilizer : MOP\n");

        if (result.length() == 0) return "No Fertilizer needed : Soil nutrients are balanced";

        return result.toString().trim();
    }

    public String getIrrigation(float rain, float humidity, String condition, float temp) {

        if ("Rain".equalsIgnoreCase(condition) || rain > 2) {
            return "IRRIGATION : Rain expected. Do not irrigate";
        }

        if ("Clouds".equalsIgnoreCase(condition) && humidity > 70) {
            return "IRRIGATION : Cloudy and humid. Skip irrigation";
        }

        if (temp > 35 && humidity < 40) {
            return "IRRIGATION : Hot and dry. Irrigate immediately";
        }

        if (humidity < 50) {
            return "IRRIGATION : Soil drying. Light irrigation needed";
        }

        return "IRRIGATION : Conditions normal. Monitor field";
    }
}