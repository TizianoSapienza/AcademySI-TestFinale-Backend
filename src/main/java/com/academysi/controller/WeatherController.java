package com.academysi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.academysi.dto.GeoCodingResponseDto;
import com.academysi.dto.WeatherPostDto;
import com.academysi.dto.WeatherResponseDto;
import com.academysi.jwt.JWTTokenNeeded;
import com.academysi.model.Weather;
import com.academysi.service.GeoCodingService;
import com.academysi.service.WeatherService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@JWTTokenNeeded
@RestController
@Path("/weather")
public class WeatherController {

    @Autowired
    private GeoCodingService geoCodingService;

    @Autowired
    private WeatherService weatherService;

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWeatherByCity(@QueryParam("city") String cityName) {
        try {
            //Info geocoding
            GeoCodingResponseDto geoCodingResponse = geoCodingService.getGeocoding(cityName);
            if (geoCodingResponse.getResults().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("City not found").build();
            }
            
            //Prendo il primo risultato corrispondente
            GeoCodingResponseDto.Result result = geoCodingResponse.getResults().get(0);
            double latitude = result.getLatitude();
            double longitude = result.getLongitude();

            //Fetch meteo
            WeatherResponseDto weatherRequest = weatherService.getWeather(latitude, longitude);
            return Response.ok(weatherRequest).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveWeather(WeatherPostDto weatherPostDto) {
        try {
            //Conversione Dto
            Weather weather = new Weather();
            weather.setCity(weatherPostDto.getCity());
            weather.setLatitude(weatherPostDto.getLatitude());
            weather.setLongitude(weatherPostDto.getLongitude());
            weather.setTime(weatherPostDto.getTime());
            weather.setTemperature(weatherPostDto.getTemperature());
            weather.setRelativeHumidity(weatherPostDto.getRelativeHumidity());
            weather.setWindSpeed(weatherPostDto.getWindSpeed());

            weatherService.saveWeather(weather);

            //Risposta JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Weather data saved successfully");

            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }
}
