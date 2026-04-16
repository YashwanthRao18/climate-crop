package backend.service;

import org.springframework.stereotype.Service;

@Service
public class CropService {

    public String recommendCrop(int n, int p, int k, float temp, float humidity, float rain) {

        if (rain > 150 && temp > 25) return " Rice";
        if (temp < 25 && humidity > 50) return "Wheat";
        if (rain < 50 && temp > 30) return "Millet";
        return "Maize";
    }

    public String getFertilizer(int n, int p, int k) {

        StringBuilder result = new StringBuilder();
        
        if (n < 50) result.append("Fertilizer: Add Urea\n");
        if (p < 40) result.append("Fertilizer: Add DAP\n");
        if (k < 40) result.append("Fertilizer: Add MOP\n");

        if (result.length() == 0) return " Soil nutrients are balanced";

        return result.toString();
    }

    // 🌧️ RAIN PREDICTION + IRRIGATION INTELLIGENCE
    public String getIrrigation(float rain, float humidity, String condition, float temp) {

        // 🌧️ Rain expected
        if ("Rain".equalsIgnoreCase(condition) || rain > 2) {
            return "Irrigation : 🌧️ Rain expected. Do NOT irrigate";
        }

        // ☁️ Cloudy + humid
        if ("Clouds".equalsIgnoreCase(condition) && humidity > 70) {
            return "Irrigation : ☁️ Cloudy & humid. Skip irrigation";
        }

        // 🔥 Very hot + dry
        if (temp > 35 && humidity < 40) {
            return "Irrigation : 🔥 Very hot & dry. Irrigate immediately";
        }

        // 💧 Moderate dryness
        if (humidity < 50) {
            return "Irrigation : 💧 Soil drying. Light irrigation needed";
        }

        return "Irrigation : ✅ Conditions normal. Monitor field";
    }
}