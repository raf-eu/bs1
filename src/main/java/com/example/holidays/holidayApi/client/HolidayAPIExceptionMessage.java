package com.example.holidays.holidayApi.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayAPIExceptionMessage {

    private int status;
    private String error;
}
