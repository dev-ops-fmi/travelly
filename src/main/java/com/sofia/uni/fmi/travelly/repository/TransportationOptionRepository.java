package com.sofia.uni.fmi.travelly.repository;

import com.sofia.uni.fmi.travelly.model.Itinerary;
import com.sofia.uni.fmi.travelly.model.TransportationOption;
import com.sofia.uni.fmi.travelly.model.TransportationOptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationOptionRepository extends JpaRepository<TransportationOption, Long> {
    List<TransportationOption> findAllByItinerary(Itinerary itinerary);

    void deleteByItinerary(Itinerary itinerary);

    @Query("SELECT a FROM TransportationOption a " +
            "WHERE a.price <= :budget " +
            "AND a.type = :filterType " +
            "AND (a.price >= :filterPriceFrom OR :filterPriceFrom = -1)" +
            "AND (a.price <= :filterPriceTo OR :filterPriceTo = -1)")
    List<TransportationOption> findTransportationOptionsByCriteria(
            @Param("budget") Double budget,
            @Param("filterType") TransportationOptionType filterType,
            @Param("filterPriceFrom") Double filterPriceFrom,
            @Param("filterPriceTo") Double filterPriceTo);
}
