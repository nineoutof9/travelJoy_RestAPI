package com.ict.traveljoy.repository.weather;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {

    @Id
    @SequenceGenerator(name = "seq_weather", sequenceName = "seq_weather", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "seq_weather", strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "temperature", nullable = false)
    private float temperature;

    @Column(name = "humidity")
    private float humidity;

    @Column(name = "precipitation")
    private float precipitation;
}
