package com.rtbanalytica.satellite.validator;


import com.rtbanalytica.satellite.exception.EmptyIdException;
import com.rtbanalytica.satellite.exception.EntityAlreadyInUseException;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import com.rtbanalytica.satellite.model.Launcher;
import com.rtbanalytica.satellite.repository.CustomerSatelliteRepository;
import com.rtbanalytica.satellite.repository.LauncherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Validator class for validating launcher entity
 * while deleting it in application database.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Component
public class DeleteLauncherValidator implements IdValidator {

    @Autowired
    private LauncherRepository launcherRepository;

    @Autowired
    private CustomerSatelliteRepository customerSatelliteRepository;

    @Override
    public void validate(String id) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new EmptyIdException("Launcher id is empty");
        }
        Optional<Launcher> launcher = launcherRepository.findById(id);
        if (launcher.isEmpty()) {
            throw new EntityNotFoundException("Launcher " + id + " is not found");
        }
        List<CustomerSatellite> customerSatellites = customerSatelliteRepository.findAllByLauncherId(id);
        if (!customerSatellites.isEmpty()) {
            throw new EntityAlreadyInUseException("Launcher " + id + " is being used by multiple satellites");
        }
    }
}
