package com.alness.restclient.api.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.alness.restclient.api.config.FeignCommonConfig;

@FeignClient(name = "genericClient", url = "${peer.api.base-url}", configuration = FeignCommonConfig.class)
public interface GenericClient {

    @GetMapping("/{path:.+}")
    feign.Response get(
            @PathVariable("path") String path,
            @RequestParam Map<String, ?> query,
            @RequestHeader Map<String, String> headers);

    @PostMapping(value = "/{path:.+}", consumes = "application/json")
    feign.Response post(
            @PathVariable("path") String path,
            @RequestParam Map<String, ?> query,
            @RequestBody Object body,
            @RequestHeader Map<String, String> headers);
}
