package com.example.holidays.holidayApi;

import com.example.holidays.holidayApi.exceptions.CountryNotFoundException;
import com.example.holidays.holidayApi.exceptions.HolidayNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Configuration
public class HolidayWebConfig {

    @ControllerAdvice
    @Slf4j
    static class GlobalControllerExceptionHandler {

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(HolidayNotFoundException.class)
        @ResponseBody
        public String handleException(HolidayNotFoundException ex) {
            log.error("Holidays not found;", ex);
            return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(CountryNotFoundException.class)
        @ResponseBody
        public String handleException(CountryNotFoundException ex) {
            log.error("Countries not found;", ex);
            return ex.getMessage();
        }

    }
}
