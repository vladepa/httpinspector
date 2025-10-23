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
public class HttpResponseInfo {
    private int statusCode;
    private String statusMessage;
    private String contentType;
    private long contentLength;
    private Map<String, String> headers;
    private String body;
    private LocalDateTime timestamp;
    private long processingTimeMs;
    private String serverInfo;
}
