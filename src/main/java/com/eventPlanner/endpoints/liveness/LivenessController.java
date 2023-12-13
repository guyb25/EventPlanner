package com.eventPlanner.endpoints.liveness;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Liveness")
public class LivenessController {
    @GetMapping("/liveness")
    public ResponseEntity<String> checkLiveness() {
        return ResponseEntity.ok("Alive");
    }
}