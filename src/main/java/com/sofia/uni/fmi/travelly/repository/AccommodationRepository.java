package com.sofia.uni.fmi.travelly.repository;

import com.sofia.uni.fmi.travelly.model.Accommodation;
import com.sofia.uni.fmi.travelly.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> findAllByItinerary(Itinerary itinerary);

    void deleteByItinerary(Itinerary itinerary);

    @Query("SELECT a FROM Accommodation a WHERE " +
            "(a.name LIKE CONCAT('%', :interest, '%') " +
            "OR a.address LIKE CONCAT('%', :interest, '%')" +
            "OR a.city LIKE CONCAT('%', :interest, '%')) " +
            "AND a.pricePerNight <= :budget " +
            "AND (a.name = :filterName OR :filterName = '')" +
            "AND (a.address = :filterAddress OR :filterAddress = '')" +
            "AND (a.city = :filterCity OR :filterCity = '')" +
            "AND (a.pricePerNight >= :filterPricePerNightFrom OR :filterPricePerNightFrom = -1)" +
            "AND (a.pricePerNight <= :filterPricePerNightTo OR :filterPricePerNightTo = -1)")
    List<Accommodation> findAccommodationsByCriteria(
            @Param("budget") Double budget,
            @Param("interest") String interest,
            @Param("filterName") String filterName,
            @Param("filterAddress") String filterAddress,
            @Param("filterCity") String filterCity,
            @Param("filterPricePerNightFrom") Double filterPricePerNightFrom,
            @Param("filterPricePerNightTo") Double filterPricePerNightTo);
}
