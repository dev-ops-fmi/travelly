package com.sofia.uni.fmi.travelly.repository;

import com.sofia.uni.fmi.travelly.model.Accommodation;
import com.sofia.uni.fmi.travelly.model.Activity;
import com.sofia.uni.fmi.travelly.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> findAllByItinerary(Itinerary itinerary);
    void deleteByItinerary(Itinerary itinerary);
    @Query("SELECT a FROM Accommodation a WHERE " +
            "(a.name LIKE CONCAT('%', :interest, '%') " +
            "OR a.address LIKE CONCAT('%', :interest, '%')" +
            "OR a.city LIKE CONCAT('%', :interest, '%')) " +
            "AND a.pricePerNight <= :budget")
    List<Accommodation> findAccommodationsByCriteria(
            @Param("budget") Double budget,
            @Param("interest") String interest);
}
