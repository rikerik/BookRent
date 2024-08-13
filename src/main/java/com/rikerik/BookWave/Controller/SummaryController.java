package com.rikerik.BookWave.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rikerik.BookWave.Service.SummaryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class SummaryController {

    SummaryService service;

    public SummaryController(SummaryService service) {
        this.service = service;
    }

    // allow requests from the URI
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/summarize")
    public ResponseEntity<String> postMethodName(@RequestBody(required = false) String text) {
        return ResponseEntity.ok().body(service.extractText(text));

    }

}
