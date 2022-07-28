package com.example.holidays.app.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class AppResponse {

    LocalDate date;
    String name1;
    String name2;
}
