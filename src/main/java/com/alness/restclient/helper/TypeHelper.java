package com.alness.restclient.helper;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TypeHelper {

    private TypeHelper() {
        throw new IllegalStateException("Helper class");
    }

    // Método genérico para cualquier tipo
    public static <T> ParameterizedTypeReference<T> typeOf(Class<T> clazz) {
        log.info("type: [{}]", clazz);
        return new ParameterizedTypeReference<T>() {
        };
    }

    // Método genérico para listas
    public static <T> ParameterizedTypeReference<List<T>> listOf(Class<T> clazz) {
        log.info("type: [{}]", clazz);
        return new ParameterizedTypeReference<List<T>>() {
        };
    }
}
