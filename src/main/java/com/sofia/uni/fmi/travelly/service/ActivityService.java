package com.sofia.uni.fmi.travelly.service;

import com.sofia.uni.fmi.travelly.dto.ActivityCreateUpdateDto;
import com.sofia.uni.fmi.travelly.mapper.ActivityMapper;
import com.sofia.uni.fmi.travelly.model.Activity;
import com.sofia.uni.fmi.travelly.model.ActivityType;
import com.sofia.uni.fmi.travelly.model.Itinerary;
import com.sofia.uni.fmi.travelly.model.Trip;
import com.sofia.uni.fmi.travelly.repository.ActivityRepository;
import com.sofia.uni.fmi.travelly.repository.ItineraryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private static final LocalDateTime COMPUTER_TIME_START = LocalDateTime.of(1970, 01, 01, 00, 00, 00);
    private ActivityRepository activityRepository;
    private ItineraryRepository itineraryRepository;
    private ActivityMapper activityMapper;
    private TripService tripService;

    public ActivityService(
            ActivityRepository activityRepository,
            ItineraryRepository itineraryRepository,
            ActivityMapper activityMapper,
            TripService tripService) {
        this.activityRepository = activityRepository;
        this.itineraryRepository = itineraryRepository;
        this.activityMapper = activityMapper;
        this.tripService = tripService;
    }

    public List<Activity> getActivitiesByItineraryId(Long itineraryId) {
        return activityRepository.findAllByItinerary(itineraryRepository.findById(itineraryId).get());
    }

    public List<Long> addActivities(List<ActivityCreateUpdateDto> activityCreateUpdateDtoList, Long itineraryId) {
        List<Long> savedActivitiesIds = new ArrayList<>();

        for (ActivityCreateUpdateDto activityCreateUpdateDto : activityCreateUpdateDtoList) {
            Activity newActivity = activityMapper.toEntity(activityCreateUpdateDto);
            Itinerary itinerary = itineraryRepository.findById(itineraryId).get();
            newActivity.setItinerary(itinerary);
            Activity savedActivity = activityRepository.save(newActivity);

            savedActivitiesIds.add(savedActivity.getId());
        }

        return savedActivitiesIds;
    }

    @Transactional
    public void deleteAllActivities(Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId).get();
        activityRepository.deleteByItinerary(itinerary);
    }

    public Activity updateActivity(Activity activity) {
        Activity existingActivity = activityRepository.findById(activity.getId()).get();
        activity.setItinerary(existingActivity.getItinerary());

        return activityRepository.save(activity);
    }

    public void deleteActivity(Long activityId) {
        activityRepository.deleteById(activityId);
    }

    public List<Activity> recommendActivities(
            Long tripId,
            ActivityType activityType,
            String activityLocation,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String description
    ) {
        Trip trip = tripService.getTripById(tripId);
        List<String> interests = Arrays.stream(trip.getInterests().split(","))
                .map(interest -> interest.trim())
                .collect(Collectors.toList());

        LocalDateTime maxStartTime;

        if (trip.getStartDate().isAfter(startTime)) {
            maxStartTime = trip.getStartDate();
        } else {
            maxStartTime = startTime;
        }

        LocalDateTime minEndTime = getMinEndTime(trip, endTime);
        Set<Activity> recommendedActivities = new HashSet<>();
        for (String interest : interests) {
            List<Activity> currentRecommendedActivities =
                    activityRepository.findActivitiesByCriteria(
                            trip.getDestination(), maxStartTime, minEndTime, interest,
                            activityType, activityLocation, description);
            currentRecommendedActivities
                    .stream()
                    .forEach(activity -> recommendedActivities.add(activity));
        }

        return recommendedActivities
                .stream()
                .collect(Collectors.toList());
    }

    private LocalDateTime getMinEndTime(Trip trip, LocalDateTime endTime) {
        if (trip.getEndDate().isAfter(endTime)
            && !endTime.isEqual(COMPUTER_TIME_START)) {
            return endTime;
        } else {
            return trip.getEndDate();
        }
    }
}

