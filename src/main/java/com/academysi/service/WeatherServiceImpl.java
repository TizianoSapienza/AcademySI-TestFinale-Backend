package com.academysi.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.academysi.dao.WeatherDao;
import com.academysi.dto.WeatherResponseDto;
import com.academysi.model.Weather;


@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;
    
    @Autowired
    private final WeatherDao weatherDao;

    @Value("${weather.api.url}")
    private String apiUrl;

    public WeatherServiceImpl(RestTemplate restTemplate, WeatherDao weatherDao) {
        this.restTemplate = restTemplate;
		this.weatherDao = weatherDao;
    }

    @Override
    public WeatherResponseDto getWeather(double latitude, double longitude) {
        //Creo un oggetto DecimalFormatSymbols con il punto come separatore (mi tornava con virgola???)
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#.#####", symbols);

        String formattedLatitude = decimalFormat.format(latitude);
        String formattedLongitude = decimalFormat.format(longitude);

        String url = apiUrl + "/v1/forecast?latitude=" + formattedLatitude + 
                     "&longitude=" + formattedLongitude +
                     "&current=temperature_2m,relative_humidity_2m,wind_speed_10m" +
                     "&timezone=GMT&forecast_days=3";
        
        return restTemplate.getForObject(url, WeatherResponseDto.class);
    }

    @Override
    public void saveWeather(Weather weather) {
        weatherDao.save(weather);
    }
}