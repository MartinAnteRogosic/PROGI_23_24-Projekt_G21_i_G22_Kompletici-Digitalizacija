package hr.fer.progi.backend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/test")
public class TestController {


    @GetMapping("/all")
    public ResponseEntity<String> getAll(){
        return ResponseEntity.ok("All");
    }

    @GetMapping("/reviser")
    public ResponseEntity<String> getReviser(){
        return ResponseEntity.ok("Reviser");
    }

    @GetMapping("/director")
    public ResponseEntity<String> getDirector(){
        return ResponseEntity.ok("Director");
    }
}
