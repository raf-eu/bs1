package com.example.holidays.app.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AppRequest {

    @NotEmpty(message = "'date' must not be empty")
    String date;

    @NotEmpty(message = "'countryCode1' must not be empty")
    String countryCode1;

    @NotEmpty(message = "'countryCode2' must not be empty")
    String countryCode2;

    LocalDate localDate;
}
