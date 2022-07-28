package com.example.holidays.app;

import com.example.holidays.app.exceptions.CodesNotFoundException;
import com.example.holidays.app.exceptions.HolidaysSizeException;
import com.example.holidays.app.model.AppRequest;
import com.example.holidays.app.model.AppResponse;
import com.example.holidays.holidayApi.country.CountryService;
import com.example.holidays.holidayApi.country.model.Country;
import com.example.holidays.holidayApi.country.model.CountryResponse;
import com.example.holidays.holidayApi.holiday.HolidayService;
import com.example.holidays.holidayApi.holiday.model.Holiday;
import com.example.holidays.holidayApi.holiday.model.HolidayResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AppServiceTest {

    private final String correctDate = "2021-03-25";
    private final String wrongDate = "2021-14-25";
    private final String existCode = "US";
    private final String notExistCode = "WT";

    private static final String DATE_WRONG_FORMAT = "Text '2021-14-25' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 14";
    private static final String WRONG_SIZE = "Wrong size of holidays list, expected 2 values, was 1";
    private static final String CODES_NOT_FOUND = "Not found more than one country code";
    private static final String holidayName1 = "holiday123";
    private static final String holidayName2 = "holiday234";
    private final LocalDate holidayDate = LocalDate.parse(correctDate);

    @Mock
    private HolidayService holidayService;

    @Mock
    CountryService countryService;

    @InjectMocks
    private AppService appService;

    @Test
    public void shouldThrowExceptionWithDateFormat() {
        assertThatThrownBy(() -> appService.prepareResult(getAppRequest(wrongDate, existCode)))
                .isInstanceOf(DateTimeException.class)
                .hasMessage(DATE_WRONG_FORMAT);
    }

    @Test
    public void shouldThrowExceptionWithCodesNotFound() {
        given(countryService.getCountryResponse()).willReturn(getCountryResponse());

        assertThatThrownBy(() -> appService.prepareResult(getAppRequest(correctDate, notExistCode)))
                .isInstanceOf(CodesNotFoundException.class)
                .hasMessage(CODES_NOT_FOUND);
    }

    @Test
    public void shouldThrowExceptionWithWrongSize() {
        given(countryService.getCountryResponse()).willReturn(getCountryResponse());
        given(holidayService.getUpcomingHolidays(anyString(), anyInt(), anyInt(), anyInt())).willReturn(getWrongSizeOfHoliday());

        assertThatThrownBy(() -> appService.prepareResult(getAppRequest(correctDate, existCode)))
                .isInstanceOf(HolidaysSizeException.class)
                .hasMessage(WRONG_SIZE);
    }

    @Test
    public void prepareResult() {
        given(countryService.getCountryResponse()).willReturn(getCountryResponse());
        given(holidayService.getUpcomingHolidays(anyString(), anyInt(), anyInt(), anyInt())).willReturn(getHolidayResponse());

        AppResponse appResponse = appService.prepareResult(getAppRequest(correctDate, existCode));

        assertThat(appResponse.getDate()).isEqualTo(holidayDate);
        assertThat(appResponse.getName1()).isEqualTo(holidayName1);
        assertThat(appResponse.getName2()).isEqualTo(holidayName2);
    }

    private AppRequest getAppRequest(String date, String code1) {
        return AppRequest.builder()
                .date(date)
                .countryCode1(code1)
                .countryCode2("PL")
                .localDate(LocalDate.parse(date)).build();
    }

    private CountryResponse getCountryResponse() {
        CountryResponse response = new CountryResponse();
        Set<Country> countries = new HashSet<>(3);
        countries.add(new Country("PL"));
        countries.add(new Country("UA"));
        countries.add(new Country("US"));
        response.setCountries(countries);
        return response;
    }

    private HolidayResponse getHolidayResponse() {
        HolidayResponse response = new HolidayResponse();
        List<Holiday> holidays = List.of(Holiday.builder().name(holidayName1).date(holidayDate).build(),
                Holiday.builder().name(holidayName2).date(holidayDate).build());
        response.setHolidays(holidays);
        return response;
    }

    private HolidayResponse getWrongSizeOfHoliday() {
        HolidayResponse response = new HolidayResponse();
        List<Holiday> holidays = List.of(Holiday.builder().name(holidayName1).date(holidayDate).build());
        response.setHolidays(holidays);
        return response;
    }

}