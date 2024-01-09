package com.sofia.uni.fmi.travelly.service;

import com.sofia.uni.fmi.travelly.dto.TripCreateDto;
import com.sofia.uni.fmi.travelly.exception.ResourceNotFoundException;
import com.sofia.uni.fmi.travelly.mapper.TripMapper;
import com.sofia.uni.fmi.travelly.model.Trip;
import com.sofia.uni.fmi.travelly.model.User;
import com.sofia.uni.fmi.travelly.repository.TripRepository;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;

    private final TripMapper tripMapper;

    public TripService(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    public Trip getTripById(Long tripId) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);

        if (!tripOptional.isPresent()) {
            throw new ResourceNotFoundException("No trip with id=" + tripId + "is present.");
        }

        return tripOptional.get();
    }

    public Long updateTrip(Trip trip) {
        Trip existingTrip = tripRepository.findById(trip.getId()).get();
        trip.setItineraries(existingTrip.getItineraries());
        return tripRepository.save(trip).getId();
    }

    public void deleteTripById(Long tripId) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();

            for (User user : trip.getUsers()) {
                user.getTrips().remove(trip);
            }
            trip.getUsers().clear();

            tripRepository.delete(trip);
        }
    }

    public Trip constructTripEntityBy(TripCreateDto tripCreateDto, User user) {
        Trip trip = tripMapper.toEntity(tripCreateDto);
        trip.setUsers(new HashSet<>());
        trip.getUsers().add(user);

        return trip;
    }

}
