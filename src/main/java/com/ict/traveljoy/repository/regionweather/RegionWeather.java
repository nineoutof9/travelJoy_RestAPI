package com.ict.traveljoy.repository.regionweather;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import com.ict.traveljoy.repository.region.Region;
import com.ict.traveljoy.repository.weather.Weather;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionWeather {

    @Id
    @SequenceGenerator(name = "seq_region_weather", sequenceName = "seq_region_weather", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "seq_region_weather", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "weather_id", nullable = false)
    private Weather weather;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "date", nullable = true)
    private LocalDate date;
}
