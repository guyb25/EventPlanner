package com.eventPlanner.endpoints.liveness;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivenessController {
    @GetMapping("/liveness")
    public ResponseEntity<String> checkLiveness() {
        return ResponseEntity.ok("Alive");
    }
}
