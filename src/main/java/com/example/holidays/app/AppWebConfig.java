package com.example.holidays.app;

import com.example.holidays.app.exceptions.CodesNotFoundException;
import com.example.holidays.app.exceptions.DateFormatException;
import com.example.holidays.app.exceptions.HolidaysSizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Configuration
public class AppWebConfig {

    @ControllerAdvice
    @Slf4j
    static class GlobalControllerExceptionHandler {

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(DateFormatException.class)
        @ResponseBody
        public String handleException(DateFormatException ex) {
            log.error("Wrong date format;", ex);
            return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        @ExceptionHandler(HolidaysSizeException.class)
        @ResponseBody
        public String handleException(HolidaysSizeException ex) {
            log.error("Wrong size of holidays list;", ex);
            return ex.getMessage();
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(CodesNotFoundException.class)
        @ResponseBody
        public String handleException(CodesNotFoundException ex) {
            log.error("Not found more than one country code;", ex);
            return ex.getMessage();
        }
    }
}
