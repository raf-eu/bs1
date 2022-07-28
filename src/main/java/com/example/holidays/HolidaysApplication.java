package com.example.holidays;

import com.example.holidays.config.SecurityProperties;
import com.example.holidays.holidayApi.config.HolidayApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class, HolidayApiProperties.class})
@EnableFeignClients
public class HolidaysApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolidaysApplication.class, args);
    }

}
