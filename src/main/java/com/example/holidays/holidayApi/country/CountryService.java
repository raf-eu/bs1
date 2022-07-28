package com.example.holidays.holidayApi.country;

import com.example.holidays.holidayApi.client.HolidayApiProvider;
import com.example.holidays.holidayApi.config.HolidayApiProperties;
import com.example.holidays.holidayApi.country.model.CountryResponse;
import com.example.holidays.holidayApi.exceptions.CountryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CountryService {

    private final HolidayApiProvider holidayApiProvider;
    private final HolidayApiProperties holidayApiProperties;

    private static final String COUNTRIES_NOT_FOUND = "Countries not found from API";

    public CountryResponse getCountryResponse() {
        return holidayApiProvider.getCountries(holidayApiProperties.getApiKey()).orElseThrow(() -> new CountryNotFoundException(COUNTRIES_NOT_FOUND));
    }
}
