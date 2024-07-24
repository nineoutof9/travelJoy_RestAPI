package com.ict.traveljoy.service.weather;

import com.ict.traveljoy.repository.weather.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDTO {
    private Long id;
    private float temperature;
    private float humidity;
    private float precipitation;

    public Weather toEntity() {
        return Weather.builder()
                .id(id)
                .temperature(temperature)
                .humidity(humidity)
                .precipitation(precipitation)
                .build();
    }

    public static WeatherDTO toDto(Weather weather) {
        return WeatherDTO.builder()
                .id(weather.getId())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .precipitation(weather.getPrecipitation())
                .build();
    }
}
