package com.example.holidays.holidayApi.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("holiday-api")
public class HolidayApiProperties {

    private String url;
    private String apiKey;

}
