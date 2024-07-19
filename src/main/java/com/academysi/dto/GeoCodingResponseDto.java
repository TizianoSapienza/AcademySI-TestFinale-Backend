package com.academysi.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoCodingResponseDto {
	
	@JsonProperty("results")
    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {
        @JsonProperty("name")
        private String name;

        @JsonProperty("latitude")
        private double latitude;

        @JsonProperty("longitude")
        private double longitude;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

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

    }
}
