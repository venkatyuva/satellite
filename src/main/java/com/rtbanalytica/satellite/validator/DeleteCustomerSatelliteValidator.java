package com.rtbanalytica.satellite.validator;

import com.rtbanalytica.satellite.exception.EmptyIdException;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import com.rtbanalytica.satellite.repository.CustomerSatelliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Validator class for validating customer satellite entity
 * while deleting it in application database.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Component
public class DeleteCustomerSatelliteValidator implements IdValidator {

    @Autowired
    private CustomerSatelliteRepository customerSatelliteRepository;

    @Override
    public void validate(String id) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new EmptyIdException("Customer Satellite id is empty");
        }
        Optional<CustomerSatellite> customerSatellite = customerSatelliteRepository.findById(id);
        if (customerSatellite.isEmpty()) {
            throw new EntityNotFoundException("Customer Satellite " + id + " is not found");
        }
    }
}
