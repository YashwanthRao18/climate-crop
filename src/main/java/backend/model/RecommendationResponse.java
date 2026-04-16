package backend.model;

public class RecommendationResponse {

    private final String crop;
    private final String fertilizer;
    private final String irrigation;
    private final float temperature;
    private final float humidity;
    private final float rain;
    private final String condition;

    public RecommendationResponse(String crop, String fertilizer, String irrigation,
                                  float temperature, float humidity, float rain, String condition) {
        this.crop = crop;
        this.fertilizer = fertilizer;
        this.irrigation = irrigation;
        this.temperature = temperature;
        this.humidity = humidity;
        this.rain = rain;
        this.condition = condition;
    }

    public String getCrop() { return crop; }
    public String getFertilizer() { return fertilizer; }
    public String getIrrigation() { return irrigation; }
    public float getTemperature() { return temperature; }
    public float getHumidity() { return humidity; }
    public float getRain() { return rain; }
    public String getCondition() { return condition; }
}