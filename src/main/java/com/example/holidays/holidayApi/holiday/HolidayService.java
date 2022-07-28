package com.example.holidays.holidayApi.holiday;

import com.example.holidays.holidayApi.client.HolidayApiProvider;
import com.example.holidays.holidayApi.config.HolidayApiProperties;
import com.example.holidays.holidayApi.exceptions.HolidayNotFoundException;
import com.example.holidays.holidayApi.holiday.model.HolidayResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HolidayService {

    private final HolidayApiProvider holidayApiProvider;
    private final HolidayApiProperties holidayApiProperties;

    private static final String HOLIDAYS_NOT_FOUND = "Holidays not found from API";

    public HolidayResponse getUpcomingHolidays(String countries, int year, int month, int day) {
        return holidayApiProvider.getHolidays(holidayApiProperties.getApiKey(), countries, year, month, day, "1").orElseThrow(() -> new HolidayNotFoundException(HOLIDAYS_NOT_FOUND));
    }
}
