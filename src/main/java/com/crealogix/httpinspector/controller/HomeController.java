package io.vladepa.httpinspector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        List<EndpointInfo> endpoints = new ArrayList<>();
        
        // HTTP Inspector endpoints
        endpoints.add(new EndpointInfo("GET", "/inspector/request", "Get detailed information about the current HTTP request"));
        endpoints.add(new EndpointInfo("POST", "/inspector/echo", "Echo the request back as a response for testing purposes"));
        endpoints.add(new EndpointInfo("GET", "/inspector/headers", "Get all request headers"));
        endpoints.add(new EndpointInfo("GET", "/inspector/parameters", "Get request parameters"));
        endpoints.add(new EndpointInfo("GET", "/inspector/client", "Get client information"));
        endpoints.add(new EndpointInfo("GET", "/inspector/health", "Health check endpoint"));
        endpoints.add(new EndpointInfo("GET", "/inspector/status", "Return response with status code based on query parameter (e.g., ?code=404)"));
        
        // Actuator endpoints
        endpoints.add(new EndpointInfo("GET", "/actuator/env", "Environment variables and properties"));
        endpoints.add(new EndpointInfo("GET", "/actuator/health", "Application health status"));
        endpoints.add(new EndpointInfo("GET", "/actuator/info", "Application information"));
        endpoints.add(new EndpointInfo("GET", "/actuator/metrics", "Application metrics"));
        
        model.addAttribute("endpoints", endpoints);
        model.addAttribute("appName", "HTTP Inspector");
        model.addAttribute("appDescription", "A Spring Boot application for inspecting HTTP requests and responses");
        
        return "home";
    }
    
    public static class EndpointInfo {
        private final String method;
        private final String path;
        private final String description;
        
        public EndpointInfo(String method, String path, String description) {
            this.method = method;
            this.path = path;
            this.description = description;
        }
        
        public String getMethod() { return method; }
        public String getPath() { return path; }
        public String getDescription() { return description; }
    }
}
