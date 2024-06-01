package com.rtbanalytica.satellite.service;

import com.rtbanalytica.satellite.client.CustomerSatelliteClient;
import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.dto.CustomerSatelliteDto;
import com.rtbanalytica.satellite.dto.CustomerSatelliteResponse;
import com.rtbanalytica.satellite.dto.CustomerSatelliteSearchDto;
import com.rtbanalytica.satellite.enums.Entity;
import com.rtbanalytica.satellite.enums.ValidatorType;
import com.rtbanalytica.satellite.exception.CreateCustomerSatelliteException;
import com.rtbanalytica.satellite.exception.DeleteCustomerSatelliteException;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import com.rtbanalytica.satellite.exception.UpdateCustomerSatelliteException;
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
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CustomerSatelliteServiceTest {

    @InjectMocks
    private CustomerSatelliteService customerSatelliteService;

    @Mock
    private CustomerSatelliteClient customerSatelliteClient;

    @Mock
    private CustomerSatelliteRepository customerSatelliteRepository;

    @Mock
    private CustomerSatelliteMapper customerSatelliteMapper;

    @Mock
    private ValidatorFactory validatorFactory;

    @Mock
    private EntityManager entityManager;

    @Mock
    private DateUtil dateUtil;

    @Test
    public void testLoadCustomerSatellites() throws EntityNotFoundException {
        CustomerSatelliteResponse customerSatelliteResponse = Mockito.mock(CustomerSatelliteResponse.class);
        Mockito.when(customerSatelliteClient.fetchCustomerSatellites()).thenReturn(customerSatelliteResponse);
        CustomerSatelliteDto customerSatelliteDto = Mockito.mock(CustomerSatelliteDto.class);
        List<CustomerSatelliteDto> customerSatelliteDtoList = new ArrayList<>();
        customerSatelliteDtoList.add(customerSatelliteDto);
        Mockito.when(customerSatelliteResponse.getCustomerSatellites()).thenReturn(customerSatelliteDtoList);
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        Mockito.when(customerSatelliteMapper.mapCustomerSatelliteDtoToCustomerSatellite(customerSatelliteDto))
                .thenReturn(customerSatellite);
        Optional<List<CustomerSatellite>> customerSatellites = customerSatelliteService.loadCustomerSatellites();
        Assert.assertFalse("Optional not empty", customerSatellites.isEmpty());
        Mockito.when(customerSatelliteClient.fetchCustomerSatellites()).thenThrow(EntityNotFoundException.class);
        Assert.assertThrows("Entity not found exception", EntityNotFoundException.class,
                () -> customerSatelliteService.loadCustomerSatellites());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFetchCustomerSatellites() {
        CriteriaBuilder criteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        CriteriaQuery<CustomerSatellite> criteriaQuery =
                (CriteriaQuery<CustomerSatellite>) Mockito.mock(CriteriaQuery.class);
        Mockito.when(criteriaBuilder.createQuery(CustomerSatellite.class)).thenReturn(criteriaQuery);
        Root<CustomerSatellite> customerSatelliteRoot = (Root<CustomerSatellite>) Mockito.mock(Root.class);
        Mockito.when(criteriaQuery.from(CustomerSatellite.class)).thenReturn(customerSatelliteRoot);
        TypedQuery<CustomerSatellite> query = (TypedQuery<CustomerSatellite>) Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        List<CustomerSatellite> customerSatellites = new ArrayList<>();
        customerSatellites.add(customerSatellite);
        Mockito.when(query.getResultList()).thenReturn(customerSatellites);
        CustomerSatelliteSearchDto customerSatelliteSearchDto = Mockito.mock(CustomerSatelliteSearchDto.class);
        Assert.assertEquals("Non empty customer satellites list", customerSatellites,
                customerSatelliteService.fetchCustomerSatellites(customerSatelliteSearchDto));
        Path<Object> customerSatellitePath = Mockito.mock(Path.class);
        Mockito.when(customerSatelliteRoot.get(SatelliteConstants.LAUNCHER_STRING)).thenReturn(customerSatellitePath);
        Mockito.when(customerSatelliteSearchDto.getId()).thenReturn(SatelliteConstants.ID);
        Mockito.when(customerSatelliteSearchDto.getCountry()).thenReturn(SatelliteConstants.COUNTRY);
        Mockito.when(customerSatelliteSearchDto.getLauncher()).thenReturn(SatelliteConstants.LAUNCHER_STRING);
        Mockito.when(customerSatelliteSearchDto.getFromDate()).thenReturn(SatelliteConstants.FROM_DATE);
        Mockito.when(customerSatelliteSearchDto.getToDate()).thenReturn(SatelliteConstants.TO_DATE);
        Mockito.when(customerSatelliteSearchDto.getMass()).thenReturn(SatelliteConstants.MASS);
        Assert.assertEquals("Non empty customer satellites list", customerSatellites,
                customerSatelliteService.fetchCustomerSatellites(customerSatelliteSearchDto));
    }

    @Test
    public void testFetchCustomerSatellite() {
        String customerSatelliteId = "CUST-01";
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        Mockito.when(customerSatelliteRepository.findById(customerSatelliteId))
                .thenReturn(Optional.of(customerSatellite));
        Assert.assertFalse("Non empty customer satellites", customerSatelliteService
                .fetchCustomerSatellite(customerSatelliteId).isEmpty());
    }

    @Test
    public void testCreateCustomerSatellites() throws CreateCustomerSatelliteException {
        BaseEntityValidator validator = Mockito.mock(BaseEntityValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.CREATE))
                .thenReturn(validator);
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        List<CustomerSatellite> customerSatellites = new ArrayList<>();
        customerSatellites.add(customerSatellite);
        Mockito.when(customerSatelliteRepository.saveAll(customerSatellites)).thenReturn(customerSatellites);
        Assert.assertEquals("Non empty customer satellites", customerSatellites, customerSatelliteService
                .createCustomerSatellites(customerSatellites));
        Mockito.when(customerSatelliteRepository.saveAll(customerSatellites)).thenThrow(new RuntimeException());
        Assert.assertThrows("Create Customer Satellite Exception", CreateCustomerSatelliteException.class,
                () -> customerSatelliteService.createCustomerSatellites(customerSatellites));
    }

    @Test
    public void testCreateCustomerSatellite() throws CreateCustomerSatelliteException {
        BaseEntityValidator validator = Mockito.mock(BaseEntityValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.CREATE))
                .thenReturn(validator);
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        Mockito.when(customerSatelliteRepository.save(customerSatellite)).thenReturn(customerSatellite);
        Assert.assertEquals("Non empty customer satellite", customerSatellite,
                customerSatelliteService.createCustomerSatellite(customerSatellite));
        Mockito.when(customerSatelliteRepository.save(customerSatellite))
                .thenThrow(RuntimeException.class);
        Assert.assertThrows("Create Customer Satellite Exception", CreateCustomerSatelliteException.class,
                () -> customerSatelliteService.createCustomerSatellite(customerSatellite));
    }

    @Test
    public void testUpdateCustomerSatellites() throws UpdateCustomerSatelliteException {
        BaseEntityValidator validator = Mockito.mock(BaseEntityValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.UPDATE))
                .thenReturn(validator);
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        List<CustomerSatellite> customerSatellites = new ArrayList<>();
        customerSatellites.add(customerSatellite);
        Mockito.when(customerSatelliteRepository.saveAll(customerSatellites)).thenReturn(customerSatellites);
        Assert.assertEquals("Non empty customer satellites", customerSatellites,
                customerSatelliteService.updateCustomerSatellites(customerSatellites));
        Mockito.when(customerSatelliteRepository.saveAll(customerSatellites)).thenThrow(new RuntimeException());
        Assert.assertThrows("Update Customer Satellite Exception", UpdateCustomerSatelliteException.class,
                () -> customerSatelliteService.updateCustomerSatellites(customerSatellites));
    }

    @Test
    public void testUpdateCustomerSatellite() throws UpdateCustomerSatelliteException {
        BaseEntityValidator validator = Mockito.mock(BaseEntityValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.UPDATE))
                .thenReturn(validator);
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        Mockito.when(customerSatelliteRepository.save(customerSatellite)).thenReturn(customerSatellite);
        String customerSatelliteId = "CUST-01";
        Assert.assertEquals("Non empty customer satellite", customerSatellite,
                customerSatelliteService.updateCustomerSatellite(customerSatelliteId, customerSatellite));
        Mockito.when(customerSatelliteRepository.save(customerSatellite)).thenThrow(new RuntimeException());
        Assert.assertThrows("Update Customer Satellite Exception", UpdateCustomerSatelliteException.class,
                () -> customerSatelliteService.updateCustomerSatellite(customerSatelliteId, customerSatellite));
    }

    @Test
    public void testDeleteCustomerSatellites() throws DeleteCustomerSatelliteException {
        IdValidator validator = Mockito.mock(IdValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.DELETE))
                .thenReturn(validator);
        String customerSatelliteId = "CUST-01";
        List<String> customerSatelliteIds = new ArrayList<>();
        customerSatelliteIds.add(customerSatelliteId);
        customerSatelliteService.deleteCustomerSatellites(customerSatelliteIds);
        Mockito.doThrow(RuntimeException.class).when(customerSatelliteRepository).deleteAllById(customerSatelliteIds);
        Assert.assertThrows("Delete Customer Satellite Exception", DeleteCustomerSatelliteException.class,
                () -> customerSatelliteService.deleteCustomerSatellites(customerSatelliteIds));
    }

    @Test
    public void testDeleteCustomerSatellite() throws DeleteCustomerSatelliteException {
        IdValidator validator = Mockito.mock(IdValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.CUSTOMER_SATELLITE, ValidatorType.DELETE))
                .thenReturn(validator);
        String customerSatelliteId = "CUST-01";
        customerSatelliteService.deleteCustomerSatellite(customerSatelliteId);
        Mockito.doThrow(RuntimeException.class).when(customerSatelliteRepository).deleteById(customerSatelliteId);
        Assert.assertThrows("Delete Customer Satellite Exception", DeleteCustomerSatelliteException.class,
                () -> customerSatelliteService.deleteCustomerSatellite(customerSatelliteId));
    }
}