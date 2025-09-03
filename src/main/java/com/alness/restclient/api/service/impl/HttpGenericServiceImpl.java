package com.alness.restclient.api.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.alness.restclient.api.client.GenericClient;
import com.alness.restclient.api.service.HttpGenericService;

import feign.Response;
import feign.codec.Decoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HttpGenericServiceImpl implements HttpGenericService {
    private final GenericClient client;
    private final Decoder decoder; // Auto-config de Spring Cloud OpenFeign

    @Override
    public String get(String path, Map<String, ?> query, @Nullable String jwt) {
        Map<String, String> headers = auth(jwt);
        feign.Response resp = client.get(trim(path), query, headers);
        return readBodyAsString(resp); // decodifica el body; puedes mapear a DTO si prefieres
    }

    @Override
    public String post(String path, Map<String, ?> query, Object body, @Nullable String jwt) {
        Map<String, String> headers = auth(jwt);
        feign.Response resp = client.post(trim(path), query, body, headers);
        return readBodyAsString(resp);
    }

    /* ================== Genéricos: Class<T> ================== */

    @Override
    public <T> T get(String path, Map<String, ?> query, @Nullable String jwt, Class<T> responseType) {
        Response resp = client.get(trim(path), query, auth(jwt));
        return decodeOrThrow(resp, responseType);
    }

    @Override
    public <T, B> T post(String path, Map<String, ?> query, B body, @Nullable String jwt, Class<T> responseType) {
        Response resp = client.post(trim(path), query, body, auth(jwt));
        return decodeOrThrow(resp, responseType);
    }

    /*
     * ================== Genéricos: ParameterizedTypeReference<T>
     * ==================
     */

    @Override
    public <T> T get(String path, Map<String, ?> query, @Nullable String jwt, ParameterizedTypeReference<T> type) {
        Response resp = client.get(trim(path), query, auth(jwt));
        return decodeOrThrow(resp, type.getType());
    }

    @Override
    public <T, B> T post(String path, Map<String, ?> query, B body, @Nullable String jwt,
            ParameterizedTypeReference<T> type) {
        Response resp = client.post(trim(path), query, body, auth(jwt));
        return decodeOrThrow(resp, type.getType());
    }

    /* ================== Helpers ================== */

    private Map<String, String> auth(@Nullable String jwt) {
        if (jwt == null || jwt.isBlank())
            return Map.of();
        String v = jwt.regionMatches(true, 0, "Bearer ", 0, 7) ? jwt : "Bearer " + jwt;
        return Map.of(HttpHeaders.AUTHORIZATION, v);
    }

    private String trim(String p) {
        return p.replaceFirst("^/+", "");
    }

    @SuppressWarnings("unchecked")
    private <T> T decodeOrThrow(Response resp, Type type) {
        try {
            int status = resp.status();
            if (status >= 200 && status < 300) {
                return (T) decoder.decode(resp, type);
            }
            String body = readBodySafe(resp);
            throw new IllegalStateException("HTTP " + status + " - Error del peer: " + body);
        } catch (IOException e) {
            throw new IllegalStateException("Error decodificando respuesta Feign", e);
        } finally {
            closeQuietly(resp);
        }
    }

    private String readBodyAsString(Response resp) {
        try {
            if (resp.body() == null)
                return "";
            return new String(resp.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Error leyendo respuesta Feign", e);
        } finally {
            closeQuietly(resp);
        }
    }

    private String readBodySafe(Response resp) {
        try {
            if (resp.body() == null)
                return "";
            return new String(resp.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ignore) {
            return "";
        }
    }

    private void closeQuietly(Response resp) {
        try {
            if (resp != null && resp.body() != null)
                resp.body().close();
        } catch (IOException ignore) {
            log.info("Error: {}", ignore.getMessage(), ignore);
        }
    }
}
