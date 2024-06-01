package com.rtbanalytica.satellite.repository;

import com.rtbanalytica.satellite.model.Launcher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for the launcher
 * entity(table) in the database.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
public interface LauncherRepository extends JpaRepository<Launcher, String> {
    Optional<Launcher> findById(String id);
}
