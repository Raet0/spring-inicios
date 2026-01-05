package ec.edu.ups.icc.fundamentos01.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;
import java.util.Map;

public class StatusController {
        @GetMapping("/api/status")
    public Map<String, Object> status() {
        return Map.of(
                "service", "Spring Boot API",
                "status", "running",
                "timestamp", LocalDateTime.now().toString());
    }
}
