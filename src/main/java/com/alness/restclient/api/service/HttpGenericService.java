package com.alness.restclient.api.service;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.lang.Nullable;

public interface HttpGenericService {
    public String get(String path, Map<String, ?> query, @Nullable String jwt);
    public String post(String path, Map<String, ?> query, Object body, @Nullable String jwt);

    // ===== Respuesta tipada por Class<T> =====
  public <T> T get(String path, Map<String, ?> query, @Nullable String jwt, Class<T> responseType);
  public <T, B> T post(String path, Map<String, ?> query, B body, @Nullable String jwt, Class<T> responseType);

  // ===== Respuesta tipada por Type (List<T>, Map<K,V>, etc.) =====
  public <T> T get(String path, Map<String, ?> query, @Nullable String jwt, ParameterizedTypeReference<T> type);
  public <T, B> T post(String path, Map<String, ?> query, B body, @Nullable String jwt, ParameterizedTypeReference<T> type);
}
