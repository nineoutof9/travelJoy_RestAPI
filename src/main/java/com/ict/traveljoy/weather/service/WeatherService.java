package com.ict.traveljoy.weather.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ict.traveljoy.weather.repository.Weather;
import com.ict.traveljoy.weather.repository.WeatherRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    // 모든 날씨 정보 검색
    public List<WeatherDTO> findAllWeathers() {
        return weatherRepository.findAll().stream()
                .map(weather -> WeatherDTO.toDto(weather))
                .collect(Collectors.toList());
    }

    // ID로 날씨 정보 검색
    public Optional<WeatherDTO> findWeatherById(Long id) {
        return weatherRepository.findById(id)
                .map(weather -> WeatherDTO.toDto(weather));
    }

    // 날씨 정보 저장
    public WeatherDTO saveWeather(WeatherDTO weatherDTO) {
        // 데이터 유효성 검증
        if (weatherDTO.getStatus() == null) {
            throw new IllegalArgumentException("날씨 상태는 비어있으면 안돼요");
        }

        Weather weather = weatherDTO.toEntity();
        weather = weatherRepository.save(weather);
        return WeatherDTO.toDto(weather);
    }
    
    // 날씨 수정
    public WeatherDTO updateWeather(Long id, WeatherDTO weatherDTO) {
        Optional<Weather> weatherOpt = weatherRepository.findById(id);

        if (weatherOpt.isPresent()) {
        	Weather weather = weatherOpt.get();
        	weather.setStatus(weatherDTO.getStatus());
        	weather.setTemperature(weatherDTO.getTemperature());
        	weather.setWindSpeed(weatherDTO.getWindSpeed());
        	weather.setHumidity(weatherDTO.getHumidity());
        	weather.setPrecipitation(weatherDTO.getPrecipitation());
        	weather.setPrecipitationPercent(weatherDTO.getPrecipitationPercent());

           
        	Weather updatedWeather = weatherRepository.save(weather);
            return WeatherDTO.toDto(updatedWeather);
        } else {
            throw new IllegalArgumentException("주어진 번호의 날씨 정보를 찾을 수 없어요");
        }
    }
    

    // ID로 날씨 정보 삭제
    public void deleteWeather(Long id) {
        if (weatherRepository.existsById(id)) {
            weatherRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("주어진 번호의 날씨 정보를 찾을 수 없어요");
        }
    }
}
