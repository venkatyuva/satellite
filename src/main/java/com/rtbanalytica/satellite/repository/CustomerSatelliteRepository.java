package com.rtbanalytica.satellite.repository;

import com.rtbanalytica.satellite.model.CustomerSatellite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for the customer_satellite
 * entity(table) in the database.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
public interface CustomerSatelliteRepository extends JpaRepository<CustomerSatellite, String> {
    List<CustomerSatellite> findAllByLauncherId(String id);
}