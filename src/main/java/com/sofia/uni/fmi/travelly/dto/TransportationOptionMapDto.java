package com.sofia.uni.fmi.travelly.dto;

import com.sofia.uni.fmi.travelly.model.TransportationOptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportationOptionMapDto {
    private TransportationOptionType type;
    private Double price;
    private Double longitude;
    private Double latitude;
}
