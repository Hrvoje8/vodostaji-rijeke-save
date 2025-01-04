package com.sava.backend.services;

import com.sava.backend.exceptions.ResourceNotFoundException;
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


    public List<Vodostaj> getByStation(String station) {
        return vodostajRepository.findAll().stream()
                .filter(v -> v.getStanica().equalsIgnoreCase(station))
                .toList();
    }

    public List<Vodostaj> getByTemperature(Float min, Float max) {
        return vodostajRepository.findAll().stream()
                .filter(v -> v.getTemperatura_zraka() >= min && v.getTemperatura_zraka() <= max)
                .toList();
    }

    public List<Vodostaj> getByHumidity(Integer min, Integer max) {
        return vodostajRepository.findAll().stream()
                .filter(v -> v.getVlaznost() >= min && v.getVlaznost() <= max)
                .toList();
    }

    public Vodostaj addVodostaj(Vodostaj vodostaj) {
        return vodostajRepository.save(vodostaj);
    }

    public boolean deleteVodostaj(Long id) {
        if (vodostajRepository.existsById(id)) {
            vodostajRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Vodostaj> updateVodostaj(Long id, Vodostaj updatedVodostaj) {
        return vodostajRepository.findById(id).map(existingVodostaj -> {
            existingVodostaj.setDatum(updatedVodostaj.getDatum());
            existingVodostaj.setVrijeme(updatedVodostaj.getVrijeme());
            existingVodostaj.setStanica(updatedVodostaj.getStanica());
            existingVodostaj.setVodostaj(updatedVodostaj.getVodostaj());
            existingVodostaj.setTemperatura_zraka(updatedVodostaj.getTemperatura_zraka());
            existingVodostaj.setVlaznost(updatedVodostaj.getVlaznost());
            existingVodostaj.setTlak_zraka(updatedVodostaj.getTlak_zraka());
            existingVodostaj.setOborine(updatedVodostaj.getOborine());
            existingVodostaj.setStanje_vremena(updatedVodostaj.getStanje_vremena());
            existingVodostaj.setVjetar(updatedVodostaj.getVjetar());
            return vodostajRepository.save(existingVodostaj);
        });
    }


}
