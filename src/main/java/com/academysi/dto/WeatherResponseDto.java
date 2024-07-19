package com.academysi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherResponseDto {

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("current")
    private CurrentWeather current;

    public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public CurrentWeather getCurrent() {
		return current;
	}

	public void setCurrent(CurrentWeather current) {
		this.current = current;
	}

	public static class CurrentWeather {
        @JsonProperty("time")
        private String time;

        @JsonProperty("interval")
        private int interval;

        @JsonProperty("temperature_2m")
        private double temperature2m;

        @JsonProperty("relative_humidity_2m")
        private int relativeHumidity2m;

        @JsonProperty("wind_speed_10m")
        private double windSpeed10m;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public int getInterval() {
			return interval;
		}

		public void setInterval(int interval) {
			this.interval = interval;
		}

		public double getTemperature2m() {
			return temperature2m;
		}

		public void setTemperature2m(double temperature2m) {
			this.temperature2m = temperature2m;
		}

		public int getRelativeHumidity2m() {
			return relativeHumidity2m;
		}

		public void setRelativeHumidity2m(int relativeHumidity2m) {
			this.relativeHumidity2m = relativeHumidity2m;
		}

		public double getWindSpeed10m() {
			return windSpeed10m;
		}

		public void setWindSpeed10m(double windSpeed10m) {
			this.windSpeed10m = windSpeed10m;
		}

    }
}