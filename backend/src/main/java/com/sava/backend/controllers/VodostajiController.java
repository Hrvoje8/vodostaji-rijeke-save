package com.sava.backend.controllers;

import com.sava.backend.DTO.ApiResponse;
import com.sava.backend.model.Vodostaj;
import com.sava.backend.services.VodostajiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class VodostajiController {

    private final VodostajiService vodostajiService;

    @Autowired
    public VodostajiController(VodostajiService vodostajService) {
        this.vodostajiService = vodostajService;
    }

    @GetMapping("/protected/vodostaji")
    public ResponseEntity<ApiResponse<List<Vodostaj>>> getVodostaji() {
        List<Vodostaj> vodostaji = vodostajiService.getAllRows();
        return ResponseEntity.ok(new ApiResponse<>(200, "Fetched all vodostaji", vodostaji));
    }

    @GetMapping("/protected/vodostaji/json-ld")
    public ResponseEntity<Map<String, Object>> getVodostajiAsJsonLd() {
        List<Vodostaj> vodostaji = vodostajiService.getAllRows();

        // Glavni JSON-LD objekt
        Map<String, Object> jsonLd = new LinkedHashMap<>();
        jsonLd.put("@context", Map.of(
                "@vocab", "https://schema.org/",
                "observationDate", "https://example.org/terms/observationDate",
                "waterLevel", "https://example.org/terms/waterLevel",
                "measuredValue", "https://www.w3.org/TR/vocab-ssn/#ObservedProperty"
        ));
        jsonLd.put("@type", "Dataset");
        jsonLd.put("name", "Vodostaji Save");
        jsonLd.put("description", "Podaci o vodostajima i vremenskim uvjetima na stanicama.");

        // Dodavanje pojedinačnih zapisa u hasPart
        List<Map<String, Object>> hasPart = vodostaji.stream().map(v -> {
            Map<String, Object> observation = new HashMap<>();
            observation.put("@type", "CreativeWork");
            observation.put("observationDate", v.getDatum());
            observation.put("measuredValue", Map.of(
                    "@type", "QuantitativeValue",
                    "value", v.getTemperatura_zraka(),
                    "unitCode", "CEL"
            ));
            observation.put("waterLevel", Map.of(
                    "@type", "QuantitativeValue",
                    "value", v.getVodostaj(),
                    "unitCode", "CM"
            ));
            observation.put("spatialCoverage", Map.of(
                    "@type", "Place",
                    "name", v.getStanica()
            ));
            return observation;
        }).toList();

        jsonLd.put("hasPart", hasPart);

        return ResponseEntity.ok(jsonLd);
    }

    @GetMapping("/public/vodostaji/{id}")
    public ResponseEntity<ApiResponse<Vodostaj>> getVodostajById(@PathVariable Long id) {
        return vodostajiService.getVodostajById(id)
                .map(vodostaj -> ResponseEntity.ok(new ApiResponse<>(200, "Fetched vodostaj", vodostaj)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Vodostaj with ID " + id + " not found", null)));
    }

    @GetMapping("/public/vodostaji/station/{station}")
    public ResponseEntity<ApiResponse<List<Vodostaj>>> getVodostajiByStation(@PathVariable String station) {
        List<Vodostaj> vodostaji = vodostajiService.getByStation(station);
        return ResponseEntity.ok(new ApiResponse<>(200, "Fetched vodostaji by station", vodostaji));
    }

    @GetMapping("/public/vodostaji/temperature/min/{min}/max/{max}")
    public ResponseEntity<ApiResponse<List<Vodostaj>>> getVodostajiByTemperature(
            @PathVariable Float min, @PathVariable Float max) {
        List<Vodostaj> vodostaji = vodostajiService.getByTemperature(min, max);
        return ResponseEntity.ok(new ApiResponse<>(200, "Fetched vodostaji by temperature range", vodostaji));
    }

    @GetMapping("/public/vodostaji/humidity/min/{min}/max/{max}")
    public ResponseEntity<ApiResponse<List<Vodostaj>>> getVodostajiByHumidity(
            @PathVariable Integer min, @PathVariable Integer max) {
        List<Vodostaj> vodostaji = vodostajiService.getByHumidity(min, max);
        return ResponseEntity.ok(new ApiResponse<>(200, "Fetched vodostaji by humidity range", vodostaji));
    }

    @PostMapping("/public/vodostaji")
    public ResponseEntity<ApiResponse<Vodostaj>> createVodostaj(@RequestBody Vodostaj vodostaj) {
        Vodostaj createdVodostaj = vodostajiService.addVodostaj(vodostaj);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Vodostaj successfully created", createdVodostaj));
    }

    @DeleteMapping("/public/vodostaji/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVodostaj(@PathVariable Long id) {
        boolean deleted = vodostajiService.deleteVodostaj(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Vodostaj deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Vodostaj with ID " + id + " not found", null));
        }
    }

    @PutMapping("/public/vodostaji/{id}")
    public ResponseEntity<ApiResponse<Vodostaj>> updateVodostaj(@PathVariable Long id, @RequestBody Vodostaj updatedVodostaj) {
        return vodostajiService.updateVodostaj(id, updatedVodostaj)
                .map(updated -> ResponseEntity.ok(new ApiResponse<>(200, "Vodostaj updated successfully", updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Vodostaj with ID " + id + " not found", null)));
    }
}
