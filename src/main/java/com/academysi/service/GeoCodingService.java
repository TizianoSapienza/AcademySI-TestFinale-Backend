package com.academysi.service;

import com.academysi.dto.GeoCodingResponseDto;

public interface GeoCodingService {
	
	GeoCodingResponseDto getGeocoding(String cityName);
}
