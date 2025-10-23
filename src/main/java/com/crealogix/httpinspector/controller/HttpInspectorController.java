package io.vladepa.httpinspector.controller;

import io.vladepa.httpinspector.model.HttpRequestInfo;
import io.vladepa.httpinspector.model.HttpResponseInfo;
import io.vladepa.httpinspector.service.HttpInspectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inspector")
@RequiredArgsConstructor
public class HttpInspectorController {

    private final HttpInspectorService httpInspectorService;

    /**
     * Get information about the current HTTP request
     */
    @GetMapping("/request")
    public ResponseEntity<HttpRequestInfo> getRequestInfo(HttpServletRequest request) {
        HttpRequestInfo requestInfo = httpInspectorService.extractRequestInfo(request);
        return ResponseEntity.ok(requestInfo);
    }

    /**
     * Echo the request back as a response for testing purposes
     */
    @PostMapping("/echo")
    public ResponseEntity<HttpResponseInfo> echoRequest(
            @RequestBody(required = false) String body,
            HttpServletRequest request) {
        HttpResponseInfo responseInfo = httpInspectorService.createEchoResponse(request, body);
        return ResponseEntity.ok(responseInfo);
    }

    /**
     * Get all request headers
     */
    @GetMapping("/headers")
    public ResponseEntity<Map<String, String>> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = httpInspectorService.extractHeaders(request);
        return ResponseEntity.ok(headers);
    }

    /**
     * Get request parameters
     */
    @GetMapping("/parameters")
    public ResponseEntity<Map<String, String[]>> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        return ResponseEntity.ok(parameters);
    }

    /**
     * Get client information
     */
    @GetMapping("/client")
    public ResponseEntity<Map<String, String>> getClientInfo(HttpServletRequest request) {
        Map<String, String> clientInfo = httpInspectorService.extractClientInfo(request);
        return ResponseEntity.ok(clientInfo);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "HTTP Inspector"));
    }

    /**
     * Return response with status code based on query parameter
     * Usage: GET /inspector/status?code=200 or GET /inspector/status?code=404
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatusByCode(
            @RequestParam(value = "code", defaultValue = "200") int statusCode,
            @RequestParam(value = "message", required = false) String message,
            HttpServletRequest request) {
        
        // Validate status code range
        if (statusCode < 100 || statusCode > 599) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "error", "Invalid status code",
                            "message", "Status code must be between 100 and 599",
                            "provided", statusCode
                    ));
        }

        // Create response body
        Map<String, Object> responseBody = Map.of(
                "statusCode", statusCode,
                "message", message != null ? message : "Status code " + statusCode,
                "timestamp", java.time.LocalDateTime.now(),
                "requestUri", request.getRequestURI(),
                "requestMethod", request.getMethod()
        );

        // Return response with the specified status code
        return ResponseEntity.status(statusCode).body(responseBody);
    }
}
