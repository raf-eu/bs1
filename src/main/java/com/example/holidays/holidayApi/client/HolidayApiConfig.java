package com.example.holidays.holidayApi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;

import static feign.FeignException.errorStatus;

@Slf4j
public class HolidayApiConfig {

    @Bean
    ErrorDecoder holidayApiErrorDecoder() {
        return (methodKey, response) -> {

            HolidayAPIExceptionMessage message = null;
            try (InputStream bodyIs = response.body()
                    .asInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                message = mapper.readValue(bodyIs, HolidayAPIExceptionMessage.class);
            } catch (IOException e) {
                return new Exception(e.getMessage());
            }

            if(response.status() >= 400 && response.status() <= 499) {
                ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.valueOf(response.status()), message.getError() != null ? message.getError() : "Holiday API application error");
                log.error(responseStatusException.getReason());
                return responseStatusException;
            }
            if(response.status() >= 500 && response.status() <= 599) {
                ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.valueOf(response.status()), "Holiday API internal server error");
                log.error(responseStatusException.getReason());
                return responseStatusException;
            }
            return errorStatus(methodKey, response);
        };
    }
}
