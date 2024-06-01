package com.rtbanalytica.satellite.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO class for response of REST API
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private T data;
}
