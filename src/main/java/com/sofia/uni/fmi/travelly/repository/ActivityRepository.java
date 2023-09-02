package com.sofia.uni.fmi.travelly.repository;

import com.sofia.uni.fmi.travelly.dto.ActivityDto;
import com.sofia.uni.fmi.travelly.model.Activity;
import com.sofia.uni.fmi.travelly.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByItinerary(Itinerary itinerary);
    void deleteByItinerary(Itinerary itinerary);

    @Query("SELECT a FROM Activity a WHERE " +
            "(a.activityLocation = :location OR a.description LIKE CONCAT('%', :interest, '%')) " +
            "AND a.startTime > :startTime " +
            "AND a.endTime < :endTime ")
    List<Activity> findActivitiesByCriteria(
            @Param("location") String location,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("interest") String interest);
}
