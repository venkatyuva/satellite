package com.rtbanalytica.satellite.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for all the
 * entities(tables) in the database.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id
    public String id;
}
