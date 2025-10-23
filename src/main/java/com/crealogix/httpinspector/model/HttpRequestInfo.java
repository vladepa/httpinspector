package io.vladepa.httpinspector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpRequestInfo {
    private String method;
    private String uri;
    private String queryString;
    private String protocol;
    private String remoteAddress;
    private String remoteHost;
    private int remotePort;
    private String localAddress;
    private String localName;
    private int localPort;
    private String serverName;
    private int serverPort;
    private String contextPath;
    private String servletPath;
    private String pathInfo;
    private String characterEncoding;
    private String contentType;
    private long contentLength;
    private Map<String, String> headers;
    private Map<String, String[]> parameters;
    private String body;
    private LocalDateTime timestamp;
    private String userAgent;
    private String referer;
    private String sessionId;
}
