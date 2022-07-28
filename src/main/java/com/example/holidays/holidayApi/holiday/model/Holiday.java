package com.example.holidays.holidayApi.holiday.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Holiday {

    private String name;
    private LocalDate date;
}
