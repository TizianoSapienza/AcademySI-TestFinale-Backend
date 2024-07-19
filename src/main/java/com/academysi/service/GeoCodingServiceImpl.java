package com.academysi.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.academysi.dto.GeoCodingResponseDto;

@Service
public class GeoCodingServiceImpl implements GeoCodingService{

	private final RestTemplate restTemplate;

    @Value("${geocoding.api.url}")
    private String apiUrl;

    public GeoCodingServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public GeoCodingResponseDto getGeocoding(String cityName) {
        String url = String.format("%s/v1/search?name=%s&count=1&language=en&format=json",
                apiUrl, cityName);
        return restTemplate.getForObject(url, GeoCodingResponseDto.class);
    }
}
