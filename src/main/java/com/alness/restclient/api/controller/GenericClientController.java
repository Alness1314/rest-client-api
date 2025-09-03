package com.alness.restclient.api.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alness.restclient.api.service.HttpGenericService;
import com.alness.restclient.common.dto.ResponseServerDto;
import com.alness.restclient.common.dto.UserRequest;
import com.alness.restclient.common.dto.UserResponse;
import com.alness.restclient.helper.TypeHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/proxy")
@RequiredArgsConstructor
@Slf4j
public class GenericClientController {
    private final HttpGenericService http;
    private String endpointBase = "users";
    private String formatEndpoint = "%s/%s";

    @GetMapping("/resource-generic/{id}")
    public ResponseEntity<Object> testGet(
            @PathVariable String id,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        Object response = http.get(String.format(formatEndpoint, endpointBase, id), Map.of(), auth, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/resource-generic")
    public ResponseEntity<Object> testGetAll(
            @RequestParam() Map<String, String> parameters,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        Object response = http.get(endpointBase, parameters, auth, TypeHelper.listOf(UserResponse.class));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resource-generic")
    public ResponseEntity<Object> testPost(
            @RequestBody UserRequest request,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        Object response = http.post(endpointBase, Map.of(), request, auth, UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/resource-generic/{id}")
    public ResponseEntity<Object> testPut(@PathVariable String id, @RequestBody UserRequest entity,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
                String endpoint = String.format("%s/%s", endpointBase, id);
                log.info("endpoint: {}", endpoint);
        Object response = http.put(endpoint, Map.of(), entity, auth,
                UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/resource-generic/{id}")
    public ResponseEntity<Object> testDelete(@PathVariable String id,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
        Object response = http.delete(String.format(formatEndpoint, endpointBase, id), Map.of(), auth,
                ResponseServerDto.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
