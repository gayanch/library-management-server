package com.github.gayanch.library.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Utility service class to dynamically construct resource URIs
 * Primary use case is to create Location header value when a REST resource is created
 */
@Service
public class UriService {
    private final String host;

    public UriService(@Value("${app.host}") String host) {
        this.host = host;
    }

    public URI createUri(String ...parts) {
        return UriComponentsBuilder.fromUriString(host).pathSegment(parts).build().toUri();
    }
}
