package com.sofia.uni.fmi.travelly.service;

import com.sofia.uni.fmi.travelly.dto.ItineraryCreateUpdateDto;
import com.sofia.uni.fmi.travelly.mapper.ItineraryMapper;
import com.sofia.uni.fmi.travelly.model.Itinerary;
import com.sofia.uni.fmi.travelly.model.Trip;
import com.sofia.uni.fmi.travelly.repository.ItineraryRepository;
import com.sofia.uni.fmi.travelly.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ItineraryService {
    private ItineraryRepository itineraryRepository;
    private ItineraryMapper itineraryMapper;
    private TripRepository tripRepository;

    public ItineraryService(ItineraryRepository itineraryRepository,
                            TripRepository tripRepository,
                            ItineraryMapper itineraryMapper) {
        this.itineraryRepository = itineraryRepository;
        this.tripRepository = tripRepository;
        this.itineraryMapper = itineraryMapper;
    }

    public List<Itinerary> getItinerariesByTripId(Long tripId) {
        return itineraryRepository.findAllByTrip(tripRepository.findById(tripId).get());
    }

    public Long addItinerary(ItineraryCreateUpdateDto itineraryCreateUpdateDto, Long tripId) {
        Itinerary newItinerary = itineraryMapper.toEntity(itineraryCreateUpdateDto);
        Trip trip = tripRepository.findById(tripId).get();
        newItinerary.setTrip(trip);
        Itinerary savedItinerary = itineraryRepository.save(newItinerary);

        return savedItinerary.getId();
    }

    @Transactional
    public void deleteAllItineraries(Long tripId) {
        Trip trip = tripRepository.findById(tripId).get();
        itineraryRepository.deleteByTrip(trip);
    }

    public Itinerary updateItinerary(Itinerary itinerary) {
        Itinerary existingItinerary = itineraryRepository.findById(itinerary.getId()).get();
        itinerary.setTrip(existingItinerary.getTrip());
        itinerary.setActivities(existingItinerary.getActivities());
        itinerary.setAccommodations(existingItinerary.getAccommodations());
        itinerary.setTransportationOptions(existingItinerary.getTransportationOptions());

        return itineraryRepository.save(itinerary);
    }

    public Itinerary getItineraryById(Long itineraryId) {
        return itineraryRepository.findById(itineraryId).get();
    }

    public void deleteItinerary(Long itineraryId) {
        itineraryRepository.deleteById(itineraryId);
    }
}
