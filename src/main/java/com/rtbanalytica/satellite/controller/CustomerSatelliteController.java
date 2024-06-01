package com.rtbanalytica.satellite.controller;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.dto.ApiResponse;
import com.rtbanalytica.satellite.dto.CustomerSatelliteSearchDto;
import com.rtbanalytica.satellite.exception.*;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import com.rtbanalytica.satellite.service.CustomerSatelliteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to interact with the
 * customer satellite service
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@RestController
@RequestMapping(SatelliteConstants.SATELLITE_V1_BASE_PATH)
public class CustomerSatelliteController {

    @Autowired
    private CustomerSatelliteService customerSatelliteService;

    /**
     * Controller method to fetch the customer satellites.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param id - customer satellite id
     * @param fromDate - from date
     * @param toDate - to date
     * @param country - country
     * @param launcherId - launcher id
     * @param mass - mass
     * @return ResponseEntity<ApiResponse<List<CustomerSatellite>>> - list of customer satellites in response entity
     * */
    @GetMapping(SatelliteConstants.CUSTOMER_SATELLITES)
    public ResponseEntity<ApiResponse<List<CustomerSatellite>>> fetchCustomerSatellites(
            HttpServletRequest request, @RequestParam(value = SatelliteConstants.ID, required = false) String id,
            @RequestParam(value = SatelliteConstants.FROM_DATE, required = false) String fromDate,
            @RequestParam(value = SatelliteConstants.TO_DATE, required = false) String toDate,
            @RequestParam(value = SatelliteConstants.COUNTRY, required = false) String country,
            @RequestParam(value = SatelliteConstants.LAUNCHER_ID, required = false) String launcherId,
            @RequestParam(value = SatelliteConstants.MASS, required = false) String mass) {

        CustomerSatelliteSearchDto customerSatelliteSearchDto = CustomerSatelliteSearchDto.builder()
                .id(id).fromDate(fromDate).toDate(toDate)
                .country(country).launcher(launcherId)
                .mass(mass).build();
        ApiResponse<List<CustomerSatellite>> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            List<CustomerSatellite> customerSatellites = customerSatelliteService
                    .fetchCustomerSatellites(customerSatelliteSearchDto);
            apiResponse.setMessage("Success");
            apiResponse.setData(customerSatellites);
            response = ResponseEntity.ok();
        } catch (Exception ex) {
            apiResponse.setMessage(ex.getMessage());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to fetch a customer satellite.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param customerSatelliteId - Customer Satellite id
     * @return ResponseEntity<ApiResponse<CustomerSatellite>> - customer satellite in response entity
     * */
    @GetMapping(SatelliteConstants.CUSTOMER_SATELLITE + SatelliteConstants.CUSTOMER_SATELLITE_ID)
    public ResponseEntity<ApiResponse<CustomerSatellite>> fetchCustomerSatellite(
            HttpServletRequest request, @PathVariable String customerSatelliteId) {

        Optional<CustomerSatellite> customerSatellite = customerSatelliteService
                .fetchCustomerSatellite(customerSatelliteId);
        ApiResponse<CustomerSatellite> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        if (customerSatellite.isPresent()) {
            apiResponse.setMessage("Success");
            apiResponse.setData(customerSatellite.get());
            response = ResponseEntity.ok();
        } else {
            apiResponse.setMessage("No customer satellite found");
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to load the customer satellites from third party API
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @return ResponseEntity<ApiResponse<List<CustomerSatellite>>> - list of customer satellites in response entity
     * */
    @PostMapping(SatelliteConstants.CUSTOMER_SATELLITES + SatelliteConstants.LOAD)
    public ResponseEntity<ApiResponse<List<CustomerSatellite>>> loadCustomerSatellites(HttpServletRequest request) {
        Optional<List<CustomerSatellite>> customerSatellites;
        ApiResponse<List<CustomerSatellite>> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            customerSatellites = customerSatelliteService.loadCustomerSatellites();
            if (customerSatellites.isPresent()) {
                apiResponse.setMessage("Success");
                apiResponse.setData(customerSatellites.get());
                response = ResponseEntity.ok();
            } else {
                apiResponse.setMessage("No customer satellites found");
                response = ResponseEntity.status(HttpStatus.NO_CONTENT);
            }
        } catch (EntityNotFoundException ex) {
            apiResponse.setMessage("Failed to load customer satellites " + ex.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to create the customer satellites
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param customerSatellites - List<CustomerSatellite>
     * @return ResponseEntity<ApiResponse<List<CustomerSatellite>>> - list of customer satellites in response entity
     * */
    @PostMapping(SatelliteConstants.CUSTOMER_SATELLITES)
    public ResponseEntity<ApiResponse<List<CustomerSatellite>>> createCustomerSatellites(
            HttpServletRequest request, @RequestBody List<CustomerSatellite> customerSatellites) {
        List<CustomerSatellite> createdCustomerSatellites;
        ApiResponse<List<CustomerSatellite>> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            createdCustomerSatellites = customerSatelliteService.createCustomerSatellites(customerSatellites);
            apiResponse.setMessage("Success");
            apiResponse.setData(createdCustomerSatellites);
            response = ResponseEntity.status(HttpStatus.CREATED);
        } catch (CreateCustomerSatelliteException ex) {
            apiResponse.setMessage(ex.getMessage());
            apiResponse.setData(customerSatellites);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to create a customer satellite
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param customerSatellite - CustomerSatellite
     * @return ResponseEntity<ApiResponse<CustomerSatellite>> - customer satellite in response entity
     * */
    @PostMapping(SatelliteConstants.CUSTOMER_SATELLITE)
    public ResponseEntity<ApiResponse<CustomerSatellite>> createCustomerSatellite(
            HttpServletRequest request, @RequestBody CustomerSatellite customerSatellite) {
        CustomerSatellite createdCustomerSatellite;
        ApiResponse<CustomerSatellite> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            createdCustomerSatellite = customerSatelliteService.createCustomerSatellite(customerSatellite);
            apiResponse.setData(createdCustomerSatellite);
            apiResponse.setMessage("Success");
            response = ResponseEntity.status(HttpStatus.CREATED);
        } catch (CreateCustomerSatelliteException ex) {
            apiResponse.setMessage(ex.getMessage());
            apiResponse.setData(customerSatellite);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to update the customer satellites
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param customerSatellites - List<CustomerSatellite>
     * @return ResponseEntity<ApiResponse<List<CustomerSatellite>>> - list of customer satellites in response entity
     * */
    @PutMapping(SatelliteConstants.CUSTOMER_SATELLITES)
    public ResponseEntity<ApiResponse<List<CustomerSatellite>>> updateCustomerSatellites(
            HttpServletRequest request, @RequestBody List<CustomerSatellite> customerSatellites) {
        List<CustomerSatellite> updatedCustomerSatellites;
        ApiResponse<List<CustomerSatellite>> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            updatedCustomerSatellites = customerSatelliteService.updateCustomerSatellites(customerSatellites);
            apiResponse.setMessage("Success");
            apiResponse.setData(updatedCustomerSatellites);
            response = ResponseEntity.ok();
        } catch (UpdateCustomerSatelliteException ex) {
            apiResponse.setMessage(ex.getMessage());
            apiResponse.setData(customerSatellites);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to update a customer satellite
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param customerSatellite - CustomerSatellite
     * @return ResponseEntity<ApiResponse<CustomerSatellite>> - customer satellite in response entity
     * */
    @PutMapping(SatelliteConstants.CUSTOMER_SATELLITE + SatelliteConstants.CUSTOMER_SATELLITE_ID)
    public ResponseEntity<ApiResponse<CustomerSatellite>> updateCustomerSatellite(
            HttpServletRequest request, @PathVariable String customerSatelliteId,
            @RequestBody CustomerSatellite customerSatellite) {
        CustomerSatellite updatedCustomerSatellite;
        ApiResponse<CustomerSatellite> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            updatedCustomerSatellite = customerSatelliteService.updateCustomerSatellite(
                    customerSatelliteId, customerSatellite);
            apiResponse.setData(updatedCustomerSatellite);
            apiResponse.setMessage("Success");
            response = ResponseEntity.ok();
        } catch (UpdateCustomerSatelliteException ex) {
            apiResponse.setMessage(ex.getMessage());
            apiResponse.setData(customerSatellite);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to delete the customer satellites
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param customerSatelliteIds - List<String>
     * @return ResponseEntity<ApiResponse<String>> - status message in response entity
     * */
    @DeleteMapping(SatelliteConstants.CUSTOMER_SATELLITES)
    public ResponseEntity<ApiResponse<String>> deleteCustomerSatellites(
            HttpServletRequest request, @RequestBody List<String> customerSatelliteIds) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            customerSatelliteService.deleteCustomerSatellites(customerSatelliteIds);
            apiResponse.setMessage("Success");
            response = ResponseEntity.ok();
        } catch (DeleteCustomerSatelliteException ex) {
            apiResponse.setMessage(ex.getMessage());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to delete a customer satellite
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param customerSatelliteId - String
     * @return ResponseEntity<ApiResponse<String>> - status message in response entity
     * */
    @DeleteMapping(SatelliteConstants.CUSTOMER_SATELLITE + SatelliteConstants.CUSTOMER_SATELLITE_ID)
    public ResponseEntity<ApiResponse<String>> deleteCustomerSatellite(
            HttpServletRequest request, @PathVariable String customerSatelliteId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            customerSatelliteService.deleteCustomerSatellite(customerSatelliteId);
            apiResponse.setMessage("Success");
            response = ResponseEntity.ok();
        } catch (DeleteCustomerSatelliteException ex) {
            apiResponse.setMessage(ex.getMessage());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }
}
