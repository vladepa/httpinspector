package io.vladepa.httpinspector.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Value("${app.show-actuator-endpoints:false}")
    private boolean showActuatorEndpoints;

    @GetMapping("/")
    public String home(Model model) {
        List<EndpointInfo> inspectorEndpoints = new ArrayList<>();
        List<EndpointInfo> actuatorEndpoints = new ArrayList<>();
        
        // HTTP Inspector endpoints
        inspectorEndpoints.add(new EndpointInfo("GET", "/inspector/request", "Get detailed information about the current HTTP request"));
        inspectorEndpoints.add(new EndpointInfo("POST", "/inspector/echo", "Echo the request back as a response for testing purposes"));
        inspectorEndpoints.add(new EndpointInfo("GET", "/inspector/headers", "Get all request headers"));
        inspectorEndpoints.add(new EndpointInfo("GET", "/inspector/parameters", "Get request parameters"));
        inspectorEndpoints.add(new EndpointInfo("GET", "/inspector/client", "Get client information"));
        inspectorEndpoints.add(new EndpointInfo("GET", "/inspector/health", "Health check endpoint"));
        inspectorEndpoints.add(new EndpointInfo("GET", "/inspector/status", "Return response with status code based on query parameter (e.g., ?code=404)"));
        
        // Actuator endpoints
        actuatorEndpoints.add(new EndpointInfo("GET", "/actuator/env", "Environment variables and properties"));
        actuatorEndpoints.add(new EndpointInfo("GET", "/actuator/health", "Application health status"));
        actuatorEndpoints.add(new EndpointInfo("GET", "/actuator/info", "Application information"));
        actuatorEndpoints.add(new EndpointInfo("GET", "/actuator/metrics", "Application metrics"));
        
        model.addAttribute("inspectorEndpoints", inspectorEndpoints);
        model.addAttribute("actuatorEndpoints", showActuatorEndpoints ? actuatorEndpoints : new ArrayList<>());
        model.addAttribute("showActuatorEndpoints", showActuatorEndpoints);
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
