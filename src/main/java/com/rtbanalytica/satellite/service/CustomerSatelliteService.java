package com.rtbanalytica.satellite.service;

import com.rtbanalytica.satellite.client.CustomerSatelliteClient;
import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.dto.CustomerSatelliteDto;
import com.rtbanalytica.satellite.dto.CustomerSatelliteResponse;
import com.rtbanalytica.satellite.dto.CustomerSatelliteSearchDto;
import com.rtbanalytica.satellite.enums.Entity;
import com.rtbanalytica.satellite.enums.ValidatorType;
import com.rtbanalytica.satellite.exception.*;
import com.rtbanalytica.satellite.factory.ValidatorFactory;
import com.rtbanalytica.satellite.mappers.CustomerSatelliteMapper;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import com.rtbanalytica.satellite.repository.CustomerSatelliteRepository;
import com.rtbanalytica.satellite.util.DateUtil;
import com.rtbanalytica.satellite.validator.BaseEntityValidator;
import com.rtbanalytica.satellite.validator.IdValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for the
 * Customer Satellite Entity
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Service
public class CustomerSatelliteService {

    private static final Logger logger = Logger.getLogger(CustomerSatelliteService.class.getName());

    @Autowired
    private CustomerSatelliteClient customerSatelliteClient;

    @Autowired
    private CustomerSatelliteRepository customerSatelliteRepository;

    @Autowired
    private CustomerSatelliteMapper customerSatelliteMapper;

    @Autowired
    private ValidatorFactory validatorFactory;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DateUtil dateUtil;

    /**
     * Method to load the customer satellites which pulls data
     * from third party API and stares in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @return Optional<List<CustomerSatellite>> - list of customer satellites
     * @throws EntityNotFoundException - Throws entity not found exception.
     * */
    public Optional<List<CustomerSatellite>> loadCustomerSatellites() throws EntityNotFoundException {
        try {
            CustomerSatelliteResponse customerSatelliteResponse = customerSatelliteClient.fetchCustomerSatellites();
            List<CustomerSatelliteDto> customerSatelliteDtoList = customerSatelliteResponse.getCustomerSatellites();
            List<CustomerSatellite> customerSatellites = customerSatelliteDtoList.stream().map(customerSatelliteDto ->
                    customerSatelliteMapper.mapCustomerSatelliteDtoToCustomerSatellite(customerSatelliteDto)).toList();
            customerSatelliteRepository.saveAll(customerSatellites);
            return Optional.of(customerSatellites);
        } catch (EntityNotFoundException ex) {
            logger.log(Level.SEVERE, "Exception while fetching customer satellites " + ex);
            throw ex;
        }
    }

    /**
     * Method to fetch the list of customer satellites
     * from our application database. This method also handles the logic of
     * search mechanism to filter based on multiple input criteria
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param customerSatelliteSearchDto - CustomerSatelliteSearchDto
     * @return List<CustomerSatellite> - list of customer satellites
     * */
    public List<CustomerSatellite> fetchCustomerSatellites(
            CustomerSatelliteSearchDto customerSatelliteSearchDto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerSatellite> criteriaQuery = criteriaBuilder.createQuery(CustomerSatellite.class);
        Root<CustomerSatellite> customerSatellite = criteriaQuery.from(CustomerSatellite.class);
        List<Predicate> predicates = new ArrayList<>();
        if (customerSatelliteSearchDto.getId() != null) {
            predicates.add(criteriaBuilder.equal(customerSatellite.get(SatelliteConstants.ID),
                    customerSatelliteSearchDto.getId()));
        }
        if (customerSatelliteSearchDto.getCountry() != null) {
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(customerSatellite.get(SatelliteConstants.COUNTRY)),
                    customerSatelliteSearchDto.getCountry().toLowerCase()));
        }
        if (customerSatelliteSearchDto.getLauncher() != null) {
            predicates.add(criteriaBuilder.equal(
                    customerSatellite.get(SatelliteConstants.LAUNCHER_STRING).get(SatelliteConstants.ID),
                    customerSatelliteSearchDto.getLauncher()));
        }
        if (customerSatelliteSearchDto.getFromDate() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(customerSatellite.get(SatelliteConstants.LAUNCH_DATE),
                    dateUtil.getSqlTimeStamp(customerSatelliteSearchDto.getFromDate())));
        }
        if (customerSatelliteSearchDto.getToDate() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(customerSatellite.get(SatelliteConstants.LAUNCH_DATE),
                    dateUtil.getSqlTimeStamp(customerSatelliteSearchDto.getToDate())));
        }
        if (customerSatelliteSearchDto.getMass() != null) {
            predicates.add(criteriaBuilder.equal(customerSatellite.get(SatelliteConstants.MASS),
                    customerSatelliteSearchDto.getMass()));
        }
        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        TypedQuery<CustomerSatellite> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    /**
     * Method to fetch a customer satellite
     * from our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param customerSatelliteId - String
     * @return Optional<CustomerSatellite> - a customer satellite
     * */
    public Optional<CustomerSatellite> fetchCustomerSatellite(String customerSatelliteId) {
        return customerSatelliteRepository.findById(customerSatelliteId);
    }

    /**
     * Method to create a list of customer satellites
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param customerSatellites - List<CustomerSatellite>
     * @return List<CustomerSatellite> - a list of customer satellites
     * @throws CreateCustomerSatelliteException - Throws create customer satellite exception
     * */
    public List<CustomerSatellite> createCustomerSatellites(List<CustomerSatellite> customerSatellites)
            throws CreateCustomerSatelliteException {
        BaseEntityValidator validator = (BaseEntityValidator) validatorFactory
                .getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.CREATE);
        try {
            for (CustomerSatellite customerSatellite : customerSatellites) {
                validator.validate(customerSatellite);
            }
            return customerSatelliteRepository.saveAll(customerSatellites);
        } catch (Exception ex) {
            throw new CreateCustomerSatelliteException(ex.getMessage());
        }
    }

    /**
     * Method to create a customer satellite
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param customerSatellite - CustomerSatellite
     * @return CustomerSatellite - a customer satellite
     * @throws CreateCustomerSatelliteException - Throws create customer satellite exception
     * */
    public CustomerSatellite createCustomerSatellite(CustomerSatellite customerSatellite)
            throws CreateCustomerSatelliteException {
        BaseEntityValidator validator = (BaseEntityValidator) validatorFactory
                .getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.CREATE);
        try {
            validator.validate(customerSatellite);
            return customerSatelliteRepository.save(customerSatellite);
        } catch (Exception ex) {
            throw new CreateCustomerSatelliteException(ex.getMessage());
        }
    }

    /**
     * Method to update a list of customer satellites
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param customerSatellites - List<CustomerSatellite>
     * @return List<CustomerSatellite> - a list of customer satellites
     * @throws UpdateCustomerSatelliteException - Throws update customer satellite exception
     * */
    public List<CustomerSatellite> updateCustomerSatellites(List<CustomerSatellite> customerSatellites)
            throws UpdateCustomerSatelliteException {
        BaseEntityValidator validator = (BaseEntityValidator) validatorFactory
                .getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.UPDATE);
        try {
            for (CustomerSatellite customerSatellite : customerSatellites) {
                validator.validate(customerSatellite);
            }
            return customerSatelliteRepository.saveAll(customerSatellites);
        } catch (Exception ex) {
            throw new UpdateCustomerSatelliteException(ex.getMessage());
        }
    }

    /**
     * Method to update a customer satellite
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param customerSatellite - CustomerSatellite
     * @return CustomerSatellite - a customer satellite
     * @throws UpdateCustomerSatelliteException - Throws update customer satellite exception
     * */
    public CustomerSatellite updateCustomerSatellite(String customerSatelliteId, CustomerSatellite customerSatellite)
            throws UpdateCustomerSatelliteException {
        customerSatellite.setId(customerSatelliteId);
        BaseEntityValidator validator = (BaseEntityValidator) validatorFactory
                .getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.UPDATE);
        try {
            validator.validate(customerSatellite);
            return customerSatelliteRepository.save(customerSatellite);
        } catch (Exception ex) {
            throw new UpdateCustomerSatelliteException(ex.getMessage());
        }
    }

    /**
     * Method to delete a list of customer satellites
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param customerSatelliteIds - List<String>
     * @throws DeleteCustomerSatelliteException - Throws delete customer satellite exception
     * */
    public void deleteCustomerSatellites(List<String> customerSatelliteIds) throws DeleteCustomerSatelliteException {
        IdValidator validator = (IdValidator) validatorFactory
                .getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.DELETE);
        try {
            for (String customerSatelliteId : customerSatelliteIds) {
                validator.validate(customerSatelliteId);
            }
            customerSatelliteRepository.deleteAllById(customerSatelliteIds);
        } catch (Exception ex) {
            throw new DeleteCustomerSatelliteException(ex.getMessage());
        }
    }

    /**
     * Method to delete a customer satellite
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param customerSatelliteId - String
     * @throws DeleteCustomerSatelliteException - Throws delete customer satellite exception
     * */
    public void deleteCustomerSatellite(String customerSatelliteId) throws DeleteCustomerSatelliteException {
        IdValidator validator = (IdValidator) validatorFactory
                .getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.DELETE);
        try {
            validator.validate(customerSatelliteId);
            customerSatelliteRepository.deleteById(customerSatelliteId);
        } catch (Exception ex) {
            throw new DeleteCustomerSatelliteException(ex.getMessage());
        }
    }
}
