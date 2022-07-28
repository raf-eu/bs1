package com.example.holidays.app;

import com.example.holidays.app.exceptions.CodesNotFoundException;
import com.example.holidays.app.exceptions.DateFormatException;
import com.example.holidays.app.exceptions.HolidaysSizeException;
import com.example.holidays.app.model.AppRequest;
import com.example.holidays.app.model.AppResponse;
import com.example.holidays.holidayApi.country.CountryService;
import com.example.holidays.holidayApi.country.model.Country;
import com.example.holidays.holidayApi.holiday.HolidayService;
import com.example.holidays.holidayApi.holiday.model.Holiday;
import com.example.holidays.holidayApi.holiday.model.HolidayResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppService {

    private static final String COMMA_SIGN = ",";
    private static final String WRONG_SIZE = "Wrong size of holidays list, expected 2 values, was ";
    private static final String CODES_NOT_FOUND = "Not found more than one country code";
    private static final String DATE_WRONG_FORMAT = "Holiday date is not in correct format. Valid format is YYYY-MM-DD";

    private final HolidayService holidayService;
    private final CountryService countryService;
    public AppResponse prepareResult(AppRequest request) {
        try {
            request.setLocalDate(LocalDate.parse(request.getDate()));
        } catch (DateTimeException ex) {
            throw new DateFormatException(DATE_WRONG_FORMAT);
        }
        boolean foundCodes = foundCodes(List.of(request.getCountryCode1(), request.getCountryCode2()));
        if(!foundCodes) throw new CodesNotFoundException(CODES_NOT_FOUND);
        List<Holiday> upcomingHolidays = getUpcomingHolidays(request).getHolidays();
        if(upcomingHolidays.size() != 2) throw new HolidaysSizeException(WRONG_SIZE + upcomingHolidays.size());
        return AppResponse.builder()
                .date(upcomingHolidays.get(0).getDate())
                .name1(upcomingHolidays.get(0).getName())
                .name2(upcomingHolidays.get(1).getName()).build();

    }

    private boolean foundCodes(List<String> countryCodes) {
        return countryService.getCountryResponse().getCountries().containsAll(countryCodes.stream().map(Country::new).collect(Collectors.toSet()));
    }

    private HolidayResponse getUpcomingHolidays(AppRequest request) {
        LocalDate holidayDate = request.getLocalDate();
        return holidayService.getUpcomingHolidays(
                combineTwoCountryCodes(request.getCountryCode1(), request.getCountryCode2()),
                holidayDate.getYear(),
                holidayDate.getMonthValue(),
                holidayDate.getDayOfMonth());
    }

    private String combineTwoCountryCodes(String country1, String country2) {
        return country1 + COMMA_SIGN + country2;
    }

}
