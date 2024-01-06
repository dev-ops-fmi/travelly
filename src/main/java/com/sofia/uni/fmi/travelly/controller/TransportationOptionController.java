package com.sofia.uni.fmi.travelly.controller;

import com.sofia.uni.fmi.travelly.dto.TransportationOptionCreateUpdateDto;
import com.sofia.uni.fmi.travelly.dto.TransportationOptionDto;
import com.sofia.uni.fmi.travelly.mapper.TransportationOptionMapper;
import com.sofia.uni.fmi.travelly.model.TransportationOption;
import com.sofia.uni.fmi.travelly.service.TransportationOptionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transportationOptions")
public class TransportationOptionController {
    private final TransportationOptionService transportationOptionService;
    private final TransportationOptionMapper transportationOptionMapper;

    public TransportationOptionController(
            TransportationOptionService transportationOptionService,
            TransportationOptionMapper transportationOptionMapper) {
        this.transportationOptionService = transportationOptionService;
        this.transportationOptionMapper = transportationOptionMapper;
    }

    @PatchMapping("{transportationOptionId}")
    public Long updateTransportationOption(
            @PathVariable Long transportationOptionId,
            @RequestBody TransportationOptionCreateUpdateDto transportationOptionCreateUpdateDto) {
        TransportationOption transportationOption
            = transportationOptionMapper.toEntity(transportationOptionCreateUpdateDto);
        transportationOption.setId(transportationOptionId);
        TransportationOption updatedTransportationOption
            = transportationOptionService.updateTransportationOption(transportationOption);
        TransportationOptionDto updatedTransportationOptionDto
            = transportationOptionMapper.toDto(updatedTransportationOption);

        return updatedTransportationOptionDto.getId();
    }

    @DeleteMapping("{transportationOptionId}")
    public void deleteTransportationOption(@PathVariable Long transportationOptionId) {
        transportationOptionService.deleteTransportationOption(transportationOptionId);
    }
}
