package com.rtbanalytica.satellite.validator;

import com.rtbanalytica.satellite.exception.EmptyIdException;
import com.rtbanalytica.satellite.exception.EntityAlreadyFoundException;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import com.rtbanalytica.satellite.model.BaseEntity;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import com.rtbanalytica.satellite.model.Launcher;
import com.rtbanalytica.satellite.repository.CustomerSatelliteRepository;
import com.rtbanalytica.satellite.repository.LauncherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Validator class for validating customer satellite entity
 * while creating it in application database.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Component
public class CreateCustomerSatelliteValidator implements BaseEntityValidator {

    @Autowired
    private CustomerSatelliteRepository customerSatelliteRepository;

    @Autowired
    private LauncherRepository launcherRepository;

    @Override
    public void validate(BaseEntity baseEntity) throws Exception {
        CustomerSatellite customerSatellite = (CustomerSatellite) baseEntity;
        if (customerSatellite.getId() == null || customerSatellite.getId().isEmpty()) {
            throw new EmptyIdException("Customer Satellite Id is empty");
        }
        Optional<CustomerSatellite> customerSatelliteOptional = customerSatelliteRepository
                .findById(customerSatellite.getId());
        if (customerSatelliteOptional.isPresent()) {
            throw new EntityAlreadyFoundException("Customer Satellite " + customerSatellite.getId() +
                    " is already available");
        }
        Optional<Launcher> launcher = launcherRepository.findById(customerSatellite.getLauncher().getId());
        if (launcher.isEmpty()) {
            throw new EntityNotFoundException("Launcher id " + customerSatellite.getLauncher().getId() +
                    " is not available");
        }
    }
}
