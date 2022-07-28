package com.example.holidays.holidayApi.client;

import com.example.holidays.holidayApi.country.model.CountryResponse;
import com.example.holidays.holidayApi.holiday.model.HolidayResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(
        name = "holiday-api",
        url = "${holiday-api.url}",
        configuration = HolidayApiConfig.class
)
public interface HolidayApiProvider {

    @GetMapping("/v1/holidays")
    Optional<HolidayResponse> getHolidays(@RequestParam("key") String key,
                         @RequestParam("country") String country,
                         @RequestParam("year") int year,
                         @RequestParam("month") int month,
                         @RequestParam("day") int day,
                         @RequestParam("upcoming") String upcoming);

    @GetMapping("/v1/countries")
    Optional<CountryResponse> getCountries(@RequestParam("key") String key);
}
