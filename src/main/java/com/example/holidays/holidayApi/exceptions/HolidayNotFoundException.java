package com.example.holidays.holidayApi.exceptions;

public class HolidayNotFoundException extends RuntimeException{

    public HolidayNotFoundException(String message) {
        super(message);
    }
}
