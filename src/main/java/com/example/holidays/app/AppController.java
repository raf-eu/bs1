package com.example.holidays.app;

import com.example.holidays.app.model.AppRequest;
import com.example.holidays.app.model.AppResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app")
@AllArgsConstructor
public class AppController {

    private final AppService appService;

    @GetMapping("/v1/holidays/two-country/incoming")
    public AppResponse appResponse(@RequestBody AppRequest request) {
        return appService.prepareResult(request);
    }
}
