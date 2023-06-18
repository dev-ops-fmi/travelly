package com.sofia.uni.fmi.travelly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDto {
    private Long id;
    private Itinerary itinerary;
    private String name;
    private String address;
    private String city;
    private Double pricePerNight;
}
