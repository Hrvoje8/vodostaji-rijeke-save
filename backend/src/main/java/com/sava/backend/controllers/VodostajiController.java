package com.sava.backend.controllers;

import com.sava.backend.DTO.ApiResponse;
import com.sava.backend.model.Vodostaj;
import com.sava.backend.services.VodostajiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class VodostajiController {

    private final VodostajiService vodostajiService;

    @Autowired
    public VodostajiController(VodostajiService vodostajService) {
        this.vodostajiService = vodostajService;
    }

    @GetMapping("/vodostaji")
    public ResponseEntity<ApiResponse<List<Vodostaj>>> getVodostaji() {
        List<Vodostaj> vodostaji = vodostajiService.getAllRows();
        return ResponseEntity.ok(new ApiResponse<>(200, "Fetched all vodostaji", vodostaji));
    }

    @GetMapping("/vodostaji/{id}")
    public ResponseEntity<ApiResponse<Vodostaj>> getVodostajById(@PathVariable Long id) {
        return vodostajiService.getVodostajById(id)
                .map(vodostaj -> ResponseEntity.ok(new ApiResponse<>(200, "Fetched vodostaj", vodostaj)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Vodostaj with ID " + id + " not found", null)));
    }

    @GetMapping("/vodostaji/station/{station}")
    public ResponseEntity<ApiResponse<List<Vodostaj>>> getVodostajiByStation(@PathVariable String station) {
        List<Vodostaj> vodostaji = vodostajiService.getByStation(station);
        return ResponseEntity.ok(new ApiResponse<>(200, "Fetched vodostaji by station", vodostaji));
    }

    @GetMapping("/vodostaji/temperature/min/{min}/max/{max}")
    public ResponseEntity<ApiResponse<List<Vodostaj>>> getVodostajiByTemperature(
            @PathVariable Float min, @PathVariable Float max) {
        List<Vodostaj> vodostaji = vodostajiService.getByTemperature(min, max);
        return ResponseEntity.ok(new ApiResponse<>(200, "Fetched vodostaji by temperature range", vodostaji));
    }

    @GetMapping("/vodostaji/humidity/min/{min}/max/{max}")
    public ResponseEntity<ApiResponse<List<Vodostaj>>> getVodostajiByHumidity(
            @PathVariable Integer min, @PathVariable Integer max) {
        List<Vodostaj> vodostaji = vodostajiService.getByHumidity(min, max);
        return ResponseEntity.ok(new ApiResponse<>(200, "Fetched vodostaji by humidity range", vodostaji));
    }

    @PostMapping("/vodostaji")
    public ResponseEntity<ApiResponse<Vodostaj>> createVodostaj(@RequestBody Vodostaj vodostaj) {
        Vodostaj createdVodostaj = vodostajiService.addVodostaj(vodostaj);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Vodostaj successfully created", createdVodostaj));
    }

    @DeleteMapping("/vodostaji/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteVodostaj(@PathVariable Long id) {
        boolean deleted = vodostajiService.deleteVodostaj(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Vodostaj deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Vodostaj with ID " + id + " not found", null));
        }
    }

    @PutMapping("/vodostaji/{id}")
    public ResponseEntity<ApiResponse<Vodostaj>> updateVodostaj(@PathVariable Long id, @RequestBody Vodostaj updatedVodostaj) {
        return vodostajiService.updateVodostaj(id, updatedVodostaj)
                .map(updated -> ResponseEntity.ok(new ApiResponse<>(200, "Vodostaj updated successfully", updated)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Vodostaj with ID " + id + " not found", null)));
    }
}
