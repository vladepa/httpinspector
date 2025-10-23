package io.vladepa.httpinspector.service;

import io.vladepa.httpinspector.model.HttpRequestInfo;
import io.vladepa.httpinspector.model.HttpResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HttpInspectorService {

    public HttpRequestInfo extractRequestInfo(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        
        try {
            String body = extractRequestBody(request);
            
            HttpRequestInfo.HttpRequestInfoBuilder builder = HttpRequestInfo.builder()
                    .method(request.getMethod())
                    .uri(request.getRequestURI())
                    .queryString(request.getQueryString())
                    .protocol(request.getProtocol())
                    .remoteAddress(request.getRemoteAddr())
                    .remoteHost(request.getRemoteHost())
                    .remotePort(request.getRemotePort())
                    .localAddress(request.getLocalAddr())
                    .localName(request.getLocalName())
                    .localPort(request.getLocalPort())
                    .serverName(request.getServerName())
                    .serverPort(request.getServerPort())
                    .contextPath(request.getContextPath())
                    .servletPath(request.getServletPath())
                    .pathInfo(request.getPathInfo())
                    .characterEncoding(request.getCharacterEncoding())
                    .contentType(request.getContentType())
                    .contentLength(request.getContentLengthLong())
                    .headers(extractHeaders(request))
                    .parameters(request.getParameterMap())
                    .body(body)
                    .timestamp(LocalDateTime.now())
                    .userAgent(request.getHeader("User-Agent"))
                    .referer(request.getHeader("Referer"))
                    .sessionId(request.getSession(false) != null ? request.getSession().getId() : null);

            return builder.build();
        } catch (Exception e) {
            log.error("Error extracting request info", e);
            throw new RuntimeException("Failed to extract request information", e);
        }
    }

    public HttpResponseInfo createEchoResponse(HttpServletRequest request, String body) {
        long startTime = System.currentTimeMillis();
        
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("X-Original-Method", request.getMethod());
        responseHeaders.put("X-Original-URI", request.getRequestURI());
        responseHeaders.put("X-Response-Time", String.valueOf(System.currentTimeMillis() - startTime));
        
        return HttpResponseInfo.builder()
                .statusCode(200)
                .statusMessage("OK")
                .contentType("application/json")
                .contentLength(body != null ? body.length() : 0)
                .headers(responseHeaders)
                .body(body)
                .timestamp(LocalDateTime.now())
                .processingTimeMs(System.currentTimeMillis() - startTime)
                .serverInfo("HTTP Inspector Service")
                .build();
    }

    public Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        
        return headers;
    }

    public Map<String, String> extractClientInfo(HttpServletRequest request) {
        Map<String, String> clientInfo = new HashMap<>();
        clientInfo.put("remoteAddress", request.getRemoteAddr());
        clientInfo.put("remoteHost", request.getRemoteHost());
        clientInfo.put("remotePort", String.valueOf(request.getRemotePort()));
        clientInfo.put("userAgent", request.getHeader("User-Agent"));
        clientInfo.put("accept", request.getHeader("Accept"));
        clientInfo.put("acceptLanguage", request.getHeader("Accept-Language"));
        clientInfo.put("acceptEncoding", request.getHeader("Accept-Encoding"));
        clientInfo.put("connection", request.getHeader("Connection"));
        clientInfo.put("xForwardedFor", request.getHeader("X-Forwarded-For"));
        clientInfo.put("xRealIP", request.getHeader("X-Real-IP"));
        
        return clientInfo;
    }

    private String extractRequestBody(HttpServletRequest request) {
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        } catch (IOException e) {
            log.warn("Could not read request body", e);
        }
        return body.toString();
    }
}
