package io.vladepa.httpinspector.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> appDetails = new HashMap<>();
        
        // Application details
        appDetails.put("name", "HTTP Inspector");
        appDetails.put("description", "A Spring Boot application for inspecting HTTP requests and responses");
        appDetails.put("version", "0.0.1-SNAPSHOT");
        appDetails.put("author", "vladepa");
        appDetails.put("contact", "io.vladepa");
        appDetails.put("license", "MIT");
        
        // Repository information
        Map<String, String> repository = new HashMap<>();
        repository.put("url", "https://github.com/vladepa/httpinspector");
        repository.put("issues", "https://github.com/vladepa/httpinspector/issues");
        repository.put("documentation", "https://github.com/vladepa/httpinspector#readme");
        appDetails.put("repository", repository);
        
        // Features
        appDetails.put("features", new String[]{
            "HTTP Request Inspection",
            "Response Testing", 
            "Client Analysis",
            "Health Monitoring",
            "Docker Support",
            "Modern UI"
        });
        
        // Endpoints summary
        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("http-inspector", 7);
        endpoints.put("actuator", 4);
        endpoints.put("total", 11);
        appDetails.put("endpoints", endpoints);
        
        // Ports configuration
        Map<String, Integer> ports = new HashMap<>();
        ports.put("application", 8080);
        ports.put("management", 8181);
        appDetails.put("ports", ports);
        
        // Runtime information
        Map<String, Object> runtime = new HashMap<>();
        runtime.put("java-version", System.getProperty("java.version"));
        runtime.put("java-vendor", System.getProperty("java.vendor"));
        runtime.put("os-name", System.getProperty("os.name"));
        runtime.put("os-version", System.getProperty("os.version"));
        runtime.put("startup-time", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        appDetails.put("runtime", runtime);
        
        // Docker information
        Map<String, Object> docker = new HashMap<>();
        docker.put("supported", true);
        docker.put("health-check", true);
        docker.put("multi-stage-build", true);
        docker.put("non-root-user", true);
        appDetails.put("docker", docker);
        
        // Status
        appDetails.put("status", "UP");
        appDetails.put("ready", true);
        
        builder.withDetail("app", appDetails);
    }
}
