package com.sava.backend.services;

import com.sava.backend.model.Vodostaj;
import com.sava.backend.repositories.VodostajRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VodostajiService {

    private final VodostajRepository vodostajRepository;

    @Autowired
    public VodostajiService(VodostajRepository vodostajRepository) {
        this.vodostajRepository = vodostajRepository;
    }

    public List<Vodostaj> getAllRows() {
        return vodostajRepository.findAll();
    }

    public Optional<Vodostaj> getVodostajById(Long id) {
        return vodostajRepository.findById(id);
    }
}
