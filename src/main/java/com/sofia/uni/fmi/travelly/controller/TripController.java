package com.sofia.uni.fmi.travelly.controller;

import com.sofia.uni.fmi.travelly.dto.AccommodationDto;
import com.sofia.uni.fmi.travelly.dto.ActivityDto;
import com.sofia.uni.fmi.travelly.dto.ItemCreateUpdateDto;
import com.sofia.uni.fmi.travelly.dto.ItemDto;
import com.sofia.uni.fmi.travelly.dto.ItineraryCreateUpdateDto;
import com.sofia.uni.fmi.travelly.dto.ItineraryDto;
import com.sofia.uni.fmi.travelly.dto.TransportationOptionDto;
import com.sofia.uni.fmi.travelly.dto.TripDto;
import com.sofia.uni.fmi.travelly.mapper.AccommodationMapper;
import com.sofia.uni.fmi.travelly.mapper.ActivityMapper;
import com.sofia.uni.fmi.travelly.mapper.ItemMapper;
import com.sofia.uni.fmi.travelly.mapper.ItineraryMapper;
import com.sofia.uni.fmi.travelly.mapper.TransportationOptionMapper;
import com.sofia.uni.fmi.travelly.mapper.TripMapper;
import com.sofia.uni.fmi.travelly.model.Accommodation;
import com.sofia.uni.fmi.travelly.model.Activity;
import com.sofia.uni.fmi.travelly.model.ActivityType;
import com.sofia.uni.fmi.travelly.model.TransportationOption;
import com.sofia.uni.fmi.travelly.model.TransportationOptionType;
import com.sofia.uni.fmi.travelly.model.Trip;
import com.sofia.uni.fmi.travelly.service.AccommodationService;
import com.sofia.uni.fmi.travelly.service.ActivityService;
import com.sofia.uni.fmi.travelly.service.ItemService;
import com.sofia.uni.fmi.travelly.service.ItineraryService;
import com.sofia.uni.fmi.travelly.service.TransportationOptionService;
import com.sofia.uni.fmi.travelly.service.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("trips")
public class TripController {

    private final TripService tripService;
    private final TripMapper tripMapper;
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ItineraryService itineraryService;
    private final ItineraryMapper itineraryMapper;
    private final ActivityService activityService;
    private final ActivityMapper activityMapper;
    private final AccommodationService accommodationService;
    private final AccommodationMapper accommodationMapper;
    private final TransportationOptionService transportationOptionService;
    private final TransportationOptionMapper transportationOptionMapper;

    public TripController(TripService tripService, TripMapper tripMapper,
                          ItemService itemService, ItemMapper itemMapper,
                          ItineraryService itineraryService, ItineraryMapper itineraryMapper,
                          ActivityService activityService, ActivityMapper activityMapper,
                          AccommodationService accommodationService, AccommodationMapper accommodationMapper,
                          TransportationOptionService transportationOptionService,
                          TransportationOptionMapper transportationOptionMapper) {
        this.tripService = tripService;
        this.tripMapper = tripMapper;
        this.itemService = itemService;
        this.itemMapper = itemMapper;
        this.itineraryService = itineraryService;
        this.itineraryMapper = itineraryMapper;
        this.activityService = activityService;
        this.activityMapper = activityMapper;
        this.accommodationService = accommodationService;
        this.accommodationMapper = accommodationMapper;
        this.transportationOptionService = transportationOptionService;
        this.transportationOptionMapper = transportationOptionMapper;
    }

    @GetMapping("{tripId}")
    public TripDto getTripById(@PathVariable Long tripId) {
        Trip trip = tripService.getTripById(tripId);
        return tripMapper.toDto(trip);
    }

    @PatchMapping
    public Long updateTripById(@RequestBody TripDto tripDto) {
        Trip trip = tripMapper.toEntity(tripDto);
        return tripService.updateTrip(trip);
    }

    @DeleteMapping("{tripId}")
    public void deleteTripById(@PathVariable Long tripId) {
        tripService.deleteTripById(tripId);
    }

    @GetMapping("{tripId}/items")
    public List<ItemDto> getItemsByTripId(@PathVariable Long tripId) {
        return itemService.getItemsByTripId(tripId)
                .stream()
                .map(item -> itemMapper.toDto(item))
                .toList();
    }

    @PostMapping("{tripId}/items")
    public List<Long> addItems(
            @PathVariable Long tripId,
            @RequestBody List<ItemCreateUpdateDto> itemCreateUpdateDtoList) {
        return itemService.addItems(itemCreateUpdateDtoList, tripId);
    }

    @DeleteMapping("{tripId}/items")
    public void deleteAllItems(@PathVariable Long tripId) {
        itemService.deleteAllItems(tripId);
    }

    @GetMapping("{tripId}/itineraries")
    public List<ItineraryDto> getItinerariesByTripId(@PathVariable Long tripId) {
        return itineraryService.getItinerariesByTripId(tripId)
                .stream()
                .map(itinerary -> itineraryMapper.toDto(itinerary))
                .toList();
    }

    @PostMapping("{tripId}/itineraries")
    public Long addItinerary(@PathVariable Long tripId,
                             @RequestBody ItineraryCreateUpdateDto itineraryCreateUpdateDto) {
        return itineraryService.addItinerary(itineraryCreateUpdateDto, tripId);
    }

    @DeleteMapping("{tripId}/itineraries")
    public void deleteAllItineraries(@PathVariable Long tripId) {
        itineraryService.deleteAllItineraries(tripId);
    }

    @GetMapping("{tripId}/activities/recommend")
    public List<ActivityDto> recommendActivities(
            @PathVariable Long tripId,
            @RequestParam ActivityType activityType,
            @RequestParam(value = "location", required = false, defaultValue = "") String location,
            @RequestParam(value = "startDate", required = false, defaultValue = "1970-01-01T00:00:00")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "1970-01-01T00:00:00")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "description", required = false, defaultValue = "") String description) {
        List<Activity> recommendedActivities = activityService
                .recommendActivities(tripId, activityType, location, startDate, endDate, description);

        List<ActivityDto> recommendedActivitiesDto =
                recommendedActivities
                        .stream()
                        .map(activity -> activityMapper.toDto(activity))
                        .collect(Collectors.toList());

        return recommendedActivitiesDto;
    }

    @GetMapping("{tripId}/accommodations/recommend")
    public List<AccommodationDto> recommendAccommodations(
            @PathVariable Long tripId,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "address", required = false, defaultValue = "") String address,
            @RequestParam(value = "city", required = false, defaultValue = "") String city,
            @RequestParam(value = "pricePerNightFrom", required = false, defaultValue = "-1") Double pricePerNightFrom,
            @RequestParam(value = "pricePerNightTo", required = false, defaultValue = "-1") Double pricePerNightTo) {
        List<Accommodation> recommendedAccommodations = accommodationService.recommendAccommodations(
                tripId, name, address, city, pricePerNightFrom, pricePerNightTo);

        List<AccommodationDto> recommendedAccommodationsDto =
                recommendedAccommodations
                        .stream()
                        .map(accommodation -> accommodationMapper.toDto(accommodation))
                        .collect(Collectors.toList());

        return recommendedAccommodationsDto;
    }

    @GetMapping("{tripId}/transportationOptions/recommend")
    public List<TransportationOptionDto> recommendTransportationOptions(
            @PathVariable Long tripId,
            @RequestParam TransportationOptionType transportationOptionType,
            @RequestParam(value = "priceFrom", required = false, defaultValue = "-1.0") Double priceFrom,
            @RequestParam(value = "priceTo", required = false, defaultValue = "-1.0") Double priceTo) {
        List<TransportationOption> recommendedTransportationOptions =
                transportationOptionService
                        .recommendTransportationOptions(tripId, transportationOptionType, priceFrom, priceTo);

        List<TransportationOptionDto> recommendedTransportationOptionDto =
                recommendedTransportationOptions
                        .stream()
                        .map(transportationOption -> transportationOptionMapper.toDto(transportationOption))
                        .collect(Collectors.toList());

        return recommendedTransportationOptionDto;
    }
}
