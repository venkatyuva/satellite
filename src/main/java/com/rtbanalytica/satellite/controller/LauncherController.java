package com.rtbanalytica.satellite.controller;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.dto.ApiResponse;
import com.rtbanalytica.satellite.exception.CreateLauncherException;
import com.rtbanalytica.satellite.exception.DeleteLauncherException;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import com.rtbanalytica.satellite.exception.UpdateLauncherException;
import com.rtbanalytica.satellite.model.Launcher;
import com.rtbanalytica.satellite.service.LauncherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller class to interact with the
 * launcher service
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@RestController
@RequestMapping(SatelliteConstants.SATELLITE_V1_BASE_PATH)
public class LauncherController {

    @Autowired
    private LauncherService launcherService;

    /**
     * Controller method to fetch the launchers.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @return ResponseEntity<ApiResponse<List<Launcher>>> - list of launchers in response entity
     * */
    @GetMapping(SatelliteConstants.LAUNCHERS)
    public ResponseEntity<ApiResponse<List<Launcher>>> fetchLaunchers(HttpServletRequest request) {
        List<Launcher> launchers = launcherService.fetchLaunchers();
        ApiResponse<List<Launcher>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Success");
        apiResponse.setData(launchers);
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * Controller method to fetch a launcher.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @return ResponseEntity<ApiResponse<Launcher>> - launcher in response entity
     * */
    @GetMapping(SatelliteConstants.LAUNCHER + SatelliteConstants.LAUNCHER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<Launcher>> fetchLauncher(
            HttpServletRequest request, @PathVariable String launcherId) {
        Optional<Launcher> launcher = launcherService.fetchLauncher(launcherId);
        ApiResponse<Launcher> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        if (launcher.isPresent()) {
            apiResponse.setMessage("Success");
            apiResponse.setData(launcher.get());
            response = ResponseEntity.ok();
        } else {
            apiResponse.setMessage("No launcher found");
            response = ResponseEntity.status(HttpStatus.NO_CONTENT);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to load the launchers from third party API
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @return ResponseEntity<ApiResponse<List<Launchers>>> - list of launchers in response entity
     * */
    @PostMapping(SatelliteConstants.LAUNCHERS + SatelliteConstants.LOAD)
    public ResponseEntity<ApiResponse<List<Launcher>>> loadLaunchers(HttpServletRequest request) {
        Optional<List<Launcher>> launchers;
        ApiResponse<List<Launcher>> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            launchers = launcherService.loadLaunchers();
            if (launchers.isPresent()) {
                apiResponse.setMessage("Success");
                apiResponse.setData(launchers.get());
                response = ResponseEntity.ok();
            } else {
                apiResponse.setMessage("No launchers found");
                response = ResponseEntity.status(HttpStatus.NO_CONTENT);
            }
        } catch (EntityNotFoundException ex) {
            apiResponse.setMessage("Failed to load launchers " + ex.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to create the launchers
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param launchers - List<Launcher>
     * @return ResponseEntity<ApiResponse<List<Launcher>>> - list of launchers in response entity
     * */
    @PostMapping(SatelliteConstants.LAUNCHERS)
    public ResponseEntity<ApiResponse<List<Launcher>>> createLaunchers(
            HttpServletRequest request, @RequestBody List<Launcher> launchers) {
        List<Launcher> createdLaunchers;
        ApiResponse<List<Launcher>> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            createdLaunchers = launcherService.createLaunchers(launchers);
            apiResponse.setMessage("Success");
            apiResponse.setData(createdLaunchers);
            response = ResponseEntity.status(HttpStatus.CREATED);
        } catch (CreateLauncherException ex) {
            apiResponse.setMessage(ex.getMessage());
            apiResponse.setData(launchers);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to create a launcher
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param launcher - Launcher
     * @return ResponseEntity<ApiResponse<Launcher>> - launcher in response entity
     * */
    @PostMapping(SatelliteConstants.LAUNCHER)
    public ResponseEntity<ApiResponse<Launcher>> createLauncher(
            HttpServletRequest request, @RequestBody Launcher launcher) {
        Launcher createdLauncher;
        ApiResponse<Launcher> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            createdLauncher = launcherService.createLauncher(launcher);
            apiResponse.setData(createdLauncher);
            apiResponse.setMessage("Success");
            response = ResponseEntity.status(HttpStatus.CREATED);
        } catch (CreateLauncherException ex) {
            apiResponse.setMessage(ex.getMessage());
            apiResponse.setData(launcher);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to update the launchers
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param launchers - List<Launcher>
     * @return ResponseEntity<ApiResponse<List<Launcher>>> - list of launchers in response entity
     * */
    @PutMapping(SatelliteConstants.LAUNCHERS)
    public ResponseEntity<ApiResponse<List<Launcher>>> updateLaunchers(
            HttpServletRequest request, @RequestBody List<Launcher> launchers) {
        List<Launcher> updatedLaunchers;
        ApiResponse<List<Launcher>> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            updatedLaunchers = launcherService.updateLaunchers(launchers);
            apiResponse.setMessage("Success");
            apiResponse.setData(updatedLaunchers);
            response = ResponseEntity.ok();
        } catch (UpdateLauncherException ex) {
            apiResponse.setMessage(ex.getMessage());
            apiResponse.setData(launchers);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to update a launcher
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param launcher - Launcher
     * @return ResponseEntity<ApiResponse<Launcher>> - launcher in response entity
     * */
    @PutMapping(SatelliteConstants.LAUNCHER + SatelliteConstants.LAUNCHER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<Launcher>> updateLauncher(
            HttpServletRequest request, @PathVariable String launcherId, @RequestBody Launcher launcher) {
        Launcher updatedLauncher;
        ApiResponse<Launcher> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            updatedLauncher = launcherService.updateLauncher(launcherId, launcher);
            apiResponse.setData(updatedLauncher);
            apiResponse.setMessage("Success");
            response = ResponseEntity.ok();
        } catch (UpdateLauncherException ex) {
            apiResponse.setMessage(ex.getMessage());
            apiResponse.setData(launcher);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to delete the launchers
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param launcherIds - List<String>
     * @return ResponseEntity<ApiResponse<String>> - status message in response entity
     * */
    @DeleteMapping(SatelliteConstants.LAUNCHERS)
    public ResponseEntity<ApiResponse<String>> deleteLaunchers(
            HttpServletRequest request, @RequestBody List<String> launcherIds) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            launcherService.deleteLaunchers(launcherIds);
            apiResponse.setMessage("Success");
            response = ResponseEntity.ok();
        } catch (DeleteLauncherException ex) {
            apiResponse.setMessage(ex.getMessage());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }

    /**
     * Controller method to delete a launcher
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param request - HttpServletRequest
     * @param launcherId - String
     * @return ResponseEntity<ApiResponse<String>> - status message in response entity
     * */
    @DeleteMapping(SatelliteConstants.LAUNCHER + SatelliteConstants.LAUNCHER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<String>> deleteLauncher(
            HttpServletRequest request, @PathVariable String launcherId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        ResponseEntity.BodyBuilder response;
        try {
            launcherService.deleteLauncher(launcherId);
            apiResponse.setMessage("Success");
            response = ResponseEntity.ok();
        } catch (DeleteLauncherException ex) {
            apiResponse.setMessage(ex.getMessage());
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        return response.body(apiResponse);
    }
}
