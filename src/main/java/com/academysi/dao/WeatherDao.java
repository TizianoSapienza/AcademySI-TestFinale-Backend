package com.academysi.dao;

import org.springframework.data.repository.CrudRepository;

import com.academysi.model.Weather;

public interface WeatherDao extends CrudRepository<Weather, Long>{

}
