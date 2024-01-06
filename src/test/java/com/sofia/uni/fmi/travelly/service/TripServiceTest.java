package com.sofia.uni.fmi.travelly.service;

import com.sofia.uni.fmi.travelly.dto.TripCreateDto;
import com.sofia.uni.fmi.travelly.exception.ResourceNotFoundException;
import com.sofia.uni.fmi.travelly.mapper.TripMapper;
import com.sofia.uni.fmi.travelly.model.Trip;
import com.sofia.uni.fmi.travelly.model.User;
import com.sofia.uni.fmi.travelly.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripMapper tripMapper;

    @InjectMocks
    private TripService tripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTripById_ExistingTrip_ReturnsTrip() {
        Long tripId = 1L;
        Trip expectedTrip = new Trip();
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(expectedTrip));

        Trip actualTrip = tripService.getTripById(tripId);

        assertEquals(expectedTrip, actualTrip);
    }

    @Test
    void getTripById_NonExistingTrip_ThrowsException() {
        Long tripId = 1L;
        when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tripService.getTripById(tripId));
    }

    // Add more test methods for other TripService functionalities
    // ...

}

