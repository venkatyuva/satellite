package com.rtbanalytica.satellite.service;

import com.rtbanalytica.satellite.client.LaunchersClient;
import com.rtbanalytica.satellite.dto.LauncherResponse;
import com.rtbanalytica.satellite.enums.Entity;
import com.rtbanalytica.satellite.enums.ValidatorType;
import com.rtbanalytica.satellite.exception.*;
import com.rtbanalytica.satellite.factory.ValidatorFactory;
import com.rtbanalytica.satellite.model.Launcher;
import com.rtbanalytica.satellite.repository.LauncherRepository;
import com.rtbanalytica.satellite.validator.BaseEntityValidator;
import com.rtbanalytica.satellite.validator.IdValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for the
 * Launcher Entity
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Service
public class LauncherService {

    private static final Logger logger = Logger.getLogger(LauncherService.class.getName());

    @Autowired
    private LaunchersClient launchersClient;

    @Autowired
    private LauncherRepository launcherRepository;

    @Autowired
    private ValidatorFactory validatorFactory;

    /**
     * Method to load the launchers which pulls data
     * from third party API and stares in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @return Optional<List<Launchers>> - list of launchers
     * @throws EntityNotFoundException - Throws entity not found exception.
     * */
    public Optional<List<Launcher>> loadLaunchers() throws EntityNotFoundException {
        try {
            LauncherResponse launcherResponse = launchersClient.fetchLaunchers();
            List<Launcher> launchers = launcherResponse.getLaunchers();
            launcherRepository.saveAll(launchers);
            return Optional.of(launchers);
        } catch (EntityNotFoundException ex) {
            logger.log(Level.SEVERE, "Exception while fetching launchers " + ex);
            throw ex;
        }
    }

    /**
     * Method to fetch the list of launchers
     * from our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @return List<Launcher> - list of launchers
     * */
    public List<Launcher> fetchLaunchers() {
        return launcherRepository.findAll();
    }

    /**
     * Method to fetch a launcher
     * from our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param launcherId - String
     * @return Optional<Launcher> - a launcher
     * */
    public Optional<Launcher> fetchLauncher(String launcherId) {
        return launcherRepository.findById(launcherId);
    }

    /**
     * Method to create a list of launchers
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param launchers - List<Launcher>
     * @return List<Launcher> - a list of launchers
     * @throws CreateLauncherException - Throws create launcher exception
     * */
    public List<Launcher> createLaunchers(List<Launcher> launchers) throws CreateLauncherException {
        BaseEntityValidator validator = (BaseEntityValidator) validatorFactory
                .getValidatorInstance(Entity.LAUNCHER, ValidatorType.CREATE);
        try {
            for (Launcher launcher : launchers) {
                validator.validate(launcher);
            }
            return launcherRepository.saveAll(launchers);
        } catch (Exception ex) {
            throw new CreateLauncherException(ex.getMessage());
        }
    }

    /**
     * Method to create a launcher
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param launcher - Launcher
     * @return Launcher - a launcher
     * @throws CreateLauncherException - Throws create launcher exception
     * */
    public Launcher createLauncher(Launcher launcher) throws CreateLauncherException {
        BaseEntityValidator validator = (BaseEntityValidator) validatorFactory
                .getValidatorInstance(Entity.LAUNCHER, ValidatorType.CREATE);
        try {
            validator.validate(launcher);
            return launcherRepository.save(launcher);
        } catch (Exception ex) {
            throw new CreateLauncherException(ex.getMessage());
        }
    }

    /**
     * Method to update a list of launchers
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param launchers - List<Launcher>
     * @return List<Launcher> - a list of launchers
     * @throws UpdateLauncherException - Throws update launcher exception
     * */
    public List<Launcher> updateLaunchers(List<Launcher> launchers) throws UpdateLauncherException {
        BaseEntityValidator validator = (BaseEntityValidator) validatorFactory
                .getValidatorInstance(Entity.LAUNCHER, ValidatorType.UPDATE);
        try {
             for (Launcher launcher : launchers) {
                 validator.validate(launcher);
             }
             return launcherRepository.saveAll(launchers);
        } catch (Exception ex) {
            throw new UpdateLauncherException(ex.getMessage());
        }
    }

    /**
     * Method to update a launcher
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param launcherId - String
     * @param launcher - Launcher
     * @return Launcher - a launcher
     * @throws UpdateLauncherException - Throws update launcher exception
     * */
    public Launcher updateLauncher(String launcherId, Launcher launcher) throws UpdateLauncherException {
        launcher.setId(launcherId);
        BaseEntityValidator validator = (BaseEntityValidator) validatorFactory
                .getValidatorInstance(Entity.LAUNCHER, ValidatorType.UPDATE);
        try {
            validator.validate(launcher);
            return launcherRepository.save(launcher);
        } catch (Exception ex) {
            throw new UpdateLauncherException(ex.getMessage());
        }
    }

    /**
     * Method to delete a list of launchers
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param launcherIds - List<String>
     * @throws DeleteLauncherException - Throws delete launcher exception
     * */
    public void deleteLaunchers(List<String> launcherIds) throws DeleteLauncherException {
        IdValidator validator = (IdValidator) validatorFactory
                .getValidatorInstance(Entity.LAUNCHER, ValidatorType.DELETE);
        try {
            for (String launcherId : launcherIds) {
                validator.validate(launcherId);
            }
            launcherRepository.deleteAllById(launcherIds);
        } catch (Exception ex) {
            throw new DeleteLauncherException(ex.getMessage());
        }
    }

    /**
     * Method to delete a launcher
     * in our application database.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param launcherId - String
     * @throws DeleteLauncherException - Throws delete launcher exception
     * */
    public void deleteLauncher(String launcherId) throws DeleteLauncherException {
        IdValidator validator = (IdValidator) validatorFactory
                .getValidatorInstance(Entity.LAUNCHER, ValidatorType.DELETE);
        try {
            validator.validate(launcherId);
            launcherRepository.deleteById(launcherId);
        } catch (Exception ex) {
            throw new DeleteLauncherException(ex.getMessage());
        }
    }
}
