package com.example.holidays.holidayApi.holiday.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayResponse {

    private List<Holiday> holidays;
}
