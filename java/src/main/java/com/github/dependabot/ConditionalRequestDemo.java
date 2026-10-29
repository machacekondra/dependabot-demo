package com.github.dependabot;

import org.springframework.http.HttpHeaders;

/**
 * Local-only example of parsing ETags from conditional request headers.
 */
public class ConditionalRequestDemo {
    public static void main(String[] args) {
        // Use benign sample values; this demo does not start an HTTP server.
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setIfMatch("\"demo-match-etag\"");
        requestHeaders.setIfNoneMatch("\"demo-none-match-etag\"");

        System.out.println("If-Match: " + requestHeaders.getIfMatch());
        System.out.println("If-None-Match: " + requestHeaders.getIfNoneMatch());
    }
}
