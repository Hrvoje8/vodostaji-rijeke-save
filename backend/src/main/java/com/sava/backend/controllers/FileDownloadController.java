package com.sava.backend.controllers;
import com.sava.backend.services.FileGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FileDownloadController {

    private final FileGenerationService fileGenerationService;

    @GetMapping("/vodostaji.csv")
    public ResponseEntity<Resource> downloadCSV() {
        File file = new File("vodostaji.csv");
        return downloadFile(file, "vodostaji.csv");
    }

    @GetMapping("/vodostaji.json")
    public ResponseEntity<Resource> downloadJSON() {
        File file = new File("vodostaji.json");
        return downloadFile(file, "vodostaji.json");
    }

    @GetMapping("/schema.json")
    public ResponseEntity<Resource> downloadSchema() {
        File file = new File("schema.json");
        return downloadFile(file, "schema.json");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshFiles() {
        try {
            fileGenerationService.generateCSV(); // Generiranje CSV
            fileGenerationService.generateJSON(); // Generiranje JSON
            return ResponseEntity.ok("Preslike uspješno osvježene.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Greška prilikom osvježavanja preslika.");
        }
    }

    private ResponseEntity<Resource> downloadFile(File file, String filename) {
        if (file.exists()) {
            Resource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}