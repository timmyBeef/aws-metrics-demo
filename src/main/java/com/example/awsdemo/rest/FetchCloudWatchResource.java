package com.example.awsdemo.rest;

import com.example.awsdemo.dto.FetchRequest;
import com.example.awsdemo.dto.FetchResponse;
import com.example.awsdemo.service.FetchCloudWatch;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "cht-alert-message")
@RestController
@RequestMapping("/cloudwatch")
public class FetchCloudWatchResource {
    private final FetchCloudWatch fetchCloudWatch;

    public FetchCloudWatchResource(FetchCloudWatch fetchCloudWatch) {
        this.fetchCloudWatch = fetchCloudWatch;
    }

    @PostMapping("/fetch")
    public List<FetchResponse> fetch(@RequestBody FetchRequest request) {
        return fetchCloudWatch.fetchData(request.getNamespace());
    }
}
