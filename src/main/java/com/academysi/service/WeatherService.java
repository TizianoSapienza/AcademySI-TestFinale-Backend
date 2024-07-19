package com.academysi.service;


import com.academysi.dto.WeatherResponseDto;
import com.academysi.model.Weather;

public interface WeatherService {
	
    WeatherResponseDto getWeather(double latitude, double longitude);
    void saveWeather(Weather weather);
}