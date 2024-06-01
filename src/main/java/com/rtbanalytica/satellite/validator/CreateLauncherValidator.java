package com.rtbanalytica.satellite.validator;

import com.rtbanalytica.satellite.exception.EmptyIdException;
import com.rtbanalytica.satellite.exception.EntityAlreadyFoundException;
import com.rtbanalytica.satellite.model.BaseEntity;
import com.rtbanalytica.satellite.model.Launcher;
import com.rtbanalytica.satellite.repository.LauncherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Validator class for validating launcher entity
 * while creating it in application database.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Component
public class CreateLauncherValidator implements BaseEntityValidator {

    @Autowired
    private LauncherRepository launcherRepository;

    @Override
    public void validate(BaseEntity baseEntity) throws Exception {
        Launcher launcher = (Launcher) baseEntity;
        if (launcher.getId() == null || launcher.getId().isEmpty()) {
            throw new EmptyIdException("Launcher id is empty");
        }
        Optional<Launcher> launcherOptional = launcherRepository.findById(launcher.getId());
        if (launcherOptional.isPresent()) {
            throw new EntityAlreadyFoundException("Launcher " + launcher.getId() + " is already available.");
        }
    }
}
