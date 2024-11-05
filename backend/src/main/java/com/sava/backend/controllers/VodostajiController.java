package com.sava.backend.controllers;

import com.sava.backend.model.Vodostaj;
import com.sava.backend.services.VodostajiService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Vodostaj> getVodostaji(){
        return vodostajiService.getAllRows();
    }

    @GetMapping("/vodostaji/{id}")
    public ResponseEntity<Vodostaj> getVodostajById(@PathVariable Long id) {
        return vodostajiService.getVodostajById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
