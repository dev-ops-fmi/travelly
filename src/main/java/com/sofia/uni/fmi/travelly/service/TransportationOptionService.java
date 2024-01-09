package com.sofia.uni.fmi.travelly.service;

import com.sofia.uni.fmi.travelly.dto.TransportationOptionCreateUpdateDto;
import com.sofia.uni.fmi.travelly.mapper.TransportationOptionMapper;
import com.sofia.uni.fmi.travelly.model.TransportationOption;
import com.sofia.uni.fmi.travelly.model.Itinerary;
import com.sofia.uni.fmi.travelly.model.TransportationOptionType;
import com.sofia.uni.fmi.travelly.model.Trip;
import com.sofia.uni.fmi.travelly.repository.TransportationOptionRepository;
import com.sofia.uni.fmi.travelly.repository.ItineraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransportationOptionService {
    private TransportationOptionRepository transportationOptionRepository;
    private ItineraryRepository itineraryRepository;
    private TransportationOptionMapper transportationOptionMapper;
    private TripService tripService;

    public TransportationOptionService(
            TransportationOptionRepository transportationOptionRepository,
            ItineraryRepository itineraryRepository,
            TransportationOptionMapper transportationOptionMapper,
            TripService tripService) {
        this.transportationOptionRepository = transportationOptionRepository;
        this.itineraryRepository = itineraryRepository;
        this.transportationOptionMapper = transportationOptionMapper;
        this.tripService = tripService;
    }

    public List<TransportationOption> getTransportationOptionsByItineraryId(Long itineraryId) {
        return transportationOptionRepository.findAllByItinerary(itineraryRepository.findById(itineraryId).get());
    }

    public List<Long> addTransportationOption(
            List<TransportationOptionCreateUpdateDto> transportationOptionCreateUpdateDtoList, Long itineraryId) {
        List<Long> savedTransportationOptionIds = new ArrayList<>();

        for (TransportationOptionCreateUpdateDto transportationOptionCreateUpdateDto
                : transportationOptionCreateUpdateDtoList) {
            TransportationOption newTransportationOption =
                    transportationOptionMapper.toEntity(transportationOptionCreateUpdateDto);
            Itinerary itinerary = itineraryRepository.findById(itineraryId).get();
            newTransportationOption.setItinerary(itinerary);
            TransportationOption savedTransportationOption =
                    transportationOptionRepository.save(newTransportationOption);

            savedTransportationOptionIds.add(savedTransportationOption.getId());
        }

        return savedTransportationOptionIds;
    }

    @Transactional
    public void deleteAllTransportationOptions(Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId).get();
        transportationOptionRepository.deleteByItinerary(itinerary);
    }

    public TransportationOption updateTransportationOption(TransportationOption transportationOption) {
        TransportationOption existingTransportationOption =
                transportationOptionRepository.findById(transportationOption.getId()).get();
        transportationOption.setItinerary(existingTransportationOption.getItinerary());

        return transportationOptionRepository.save(transportationOption);
    }

    public void deleteTransportationOption(Long transportationOptionId) {
        transportationOptionRepository.deleteById(transportationOptionId);
    }

    public List<TransportationOption> recommendTransportationOptions(
            Long tripId, TransportationOptionType transportationOptionType, Double priceFrom, Double priceTo) {
        Trip trip = tripService.getTripById(tripId);

        List<TransportationOption> recommendedTransportationOptions =
                transportationOptionRepository
                        .findTransportationOptionsByCriteria(
                                trip.getBudget(), transportationOptionType, priceFrom, priceTo);

        return recommendedTransportationOptions;
    }
}
