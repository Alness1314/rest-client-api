package com.alness.restclient.api.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alness.restclient.api.service.HttpGenericService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/proxy")
@RequiredArgsConstructor
public class GenericClientController {
    private final HttpGenericService http;

    @GetMapping("/resource/{id}")
    public String test(
            @PathVariable String id,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        return http.get("/api/v2/pokemon/" + id, Map.of(), auth);
    }
}
