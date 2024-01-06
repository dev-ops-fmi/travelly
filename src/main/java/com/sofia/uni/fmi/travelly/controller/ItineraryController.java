package com.sofia.uni.fmi.travelly.controller;

import com.sofia.uni.fmi.travelly.dto.AccommodationCreateUpdateDto;
import com.sofia.uni.fmi.travelly.dto.AccommodationDto;
import com.sofia.uni.fmi.travelly.dto.AccommodationMapDto;
import com.sofia.uni.fmi.travelly.dto.ActivityCreateUpdateDto;
import com.sofia.uni.fmi.travelly.dto.ActivityDto;
import com.sofia.uni.fmi.travelly.dto.ActivityMapDto;
import com.sofia.uni.fmi.travelly.dto.ItineraryCreateUpdateDto;
import com.sofia.uni.fmi.travelly.dto.ItineraryDto;
import com.sofia.uni.fmi.travelly.dto.TransportationOptionCreateUpdateDto;
import com.sofia.uni.fmi.travelly.dto.TransportationOptionDto;
import com.sofia.uni.fmi.travelly.dto.TransportationOptionMapDto;
import com.sofia.uni.fmi.travelly.mapper.AccommodationMapper;
import com.sofia.uni.fmi.travelly.mapper.ActivityMapper;
import com.sofia.uni.fmi.travelly.mapper.ItineraryMapper;
import com.sofia.uni.fmi.travelly.mapper.TransportationOptionMapper;
import com.sofia.uni.fmi.travelly.model.Itinerary;
import com.sofia.uni.fmi.travelly.service.AccommodationService;
import com.sofia.uni.fmi.travelly.service.ActivityService;
import com.sofia.uni.fmi.travelly.service.ItineraryService;
import com.sofia.uni.fmi.travelly.service.TransportationOptionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("itineraries")
public class ItineraryController {
    private ItineraryService itineraryService;
    private ItineraryMapper itineraryMapper;

    private ActivityService activityService;
    private ActivityMapper activityMapper;

    private AccommodationService accommodationService;
    private AccommodationMapper accommodationMapper;

    private TransportationOptionService transportationOptionService;
    private TransportationOptionMapper transportationOptionMapper;

    public ItineraryController(
            ItineraryService itineraryService,
            ItineraryMapper itineraryMapper,
            ActivityService activityService,
            ActivityMapper activityMapper,
            AccommodationService accommodationService,
            AccommodationMapper accommodationMapper,
            TransportationOptionService transportationOptionService,
            TransportationOptionMapper transportationOptionMapper
    ) {
        this.itineraryService = itineraryService;
        this.itineraryMapper = itineraryMapper;
        this.activityService = activityService;
        this.activityMapper = activityMapper;
        this.accommodationService = accommodationService;
        this.accommodationMapper = accommodationMapper;
        this.transportationOptionService = transportationOptionService;
        this.transportationOptionMapper = transportationOptionMapper;
    }

    @PatchMapping("{itineraryId}")
    public Long updateItinerary(@PathVariable Long itineraryId,
                                @RequestBody ItineraryCreateUpdateDto itineraryCreateUpdateDto) {
        Itinerary itinerary = itineraryMapper.toEntity(itineraryCreateUpdateDto);
        itinerary.setId(itineraryId);
        Itinerary updatedItinerary = itineraryService.updateItinerary(itinerary);
        ItineraryDto updatedItineraryDto = itineraryMapper.toDto(updatedItinerary);

        return updatedItineraryDto.getId();
    }

    @GetMapping("{itineraryId}")
    public ItineraryDto getItineraryById(@PathVariable Long itineraryId) {
        Itinerary itinerary = itineraryService.getItineraryById(itineraryId);
        ItineraryDto itineraryDto = itineraryMapper.toDto(itinerary);

        return itineraryDto;
    }

    @DeleteMapping("{itineraryId}")
    public void deleteItinerary(@PathVariable Long itineraryId) {
        itineraryService.deleteItinerary(itineraryId);
    }

    @GetMapping("{itineraryId}/activities")
    public List<ActivityDto> getActivitiesByItineraryId(@PathVariable Long itineraryId) {
        return activityService.getActivitiesByItineraryId(itineraryId)
                .stream()
                .map(activity -> activityMapper.toDto(activity))
                .toList();
    }

    @GetMapping("{itineraryId}/activities/map")
    public List<ActivityMapDto> getMapActivitiesByItineraryId(@PathVariable Long itineraryId) {
        return activityService.getActivitiesByItineraryId(itineraryId)
                .stream()
                .map(activity -> activityMapper.toMapDto(activity))
                .toList();
    }

    @PostMapping("{itineraryId}/activities")
    public List<Long> addActivities(
            @PathVariable Long itineraryId,
            @RequestBody List<ActivityCreateUpdateDto> activityCreateUpdateDtoList) {
        return activityService.addActivities(activityCreateUpdateDtoList, itineraryId);
    }

    @DeleteMapping("{itineraryId}/activities")
    public void deleteAllActivities(@PathVariable Long itineraryId) {
        activityService.deleteAllActivities(itineraryId);
    }

    @GetMapping("{itineraryId}/accommodations")
    public List<AccommodationDto> getAccommodationsByItineraryId(@PathVariable Long itineraryId) {
        return accommodationService.getAccommodationsByItineraryId(itineraryId)
                .stream()
                .map(accommodation -> accommodationMapper.toDto(accommodation))
                .toList();
    }

    @GetMapping("{itineraryId}/accommodations/map")
    public List<AccommodationMapDto> getMapAccommodationsByItineraryId(@PathVariable Long itineraryId) {
        return accommodationService.getAccommodationsByItineraryId(itineraryId)
                .stream()
                .map(accommodation -> accommodationMapper.toMapDto(accommodation))
                .toList();
    }

    @PostMapping("{itineraryId}/accommodations")
    public List<Long> addAccommodation(
            @PathVariable Long itineraryId,
            @RequestBody List<AccommodationCreateUpdateDto> accommodationCreateUpdateDtoList) {
        return accommodationService.addAccommodations(accommodationCreateUpdateDtoList, itineraryId);
    }

    @DeleteMapping("{itineraryId}/accommodations")
    public void deleteAllAccommodations(@PathVariable Long itineraryId) {
        accommodationService.deleteAllAccommodations(itineraryId);
    }

    @GetMapping("{itineraryId}/transportationOptions")
    public List<TransportationOptionDto> getTransportationOptionsByItineraryId(@PathVariable Long itineraryId) {
        return transportationOptionService.getTransportationOptionsByItineraryId(itineraryId)
                .stream()
                .map(transportationOption -> transportationOptionMapper.toDto(transportationOption))
                .toList();
    }

    @GetMapping("{itineraryId}/transportationOptions/map")
    public List<TransportationOptionMapDto> getMapTransportationOptionsByItineraryId(@PathVariable Long itineraryId) {
        return transportationOptionService.getTransportationOptionsByItineraryId(itineraryId)
                .stream()
                .map(transportationOption -> transportationOptionMapper.toMapDto(transportationOption))
                .toList();
    }

    @PostMapping("{itineraryId}/transportationOptions")
    public List<Long> addTransportationOption(
            @PathVariable Long itineraryId,
            @RequestBody List<TransportationOptionCreateUpdateDto> transportationOptionCreateUpdateDtoList) {
        return transportationOptionService
            .addTransportationOption(transportationOptionCreateUpdateDtoList, itineraryId);
    }

    @DeleteMapping("{itineraryId}/transportationOptions")
    public void deleteAllTransportationOptions(@PathVariable Long itineraryId) {
        transportationOptionService.deleteAllTransportationOptions(itineraryId);
    }
}
