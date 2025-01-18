package com.sava.backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sava.backend.model.Vodostaj;
import com.sava.backend.repositories.VodostajRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileGenerationService {

    private final VodostajRepository vodostajRepository;

    public void generateCSV() throws IOException {
        List<Vodostaj> vodostaji = vodostajRepository.findAll();

        try (FileWriter writer = new FileWriter("vodostaji.csv")) {
            writer.append("id,datum,vrijeme,stanica,vodostaj,temperatura_zraka,vlaznost,tlak_zraka,oborine,stanje_vremena,vjetar_smjer,vjetar_brzina\n");

            for (Vodostaj v : vodostaji) {
                writer.append(v.getId() + ",")
                        .append(v.getDatum() + ",")
                        .append(v.getVrijeme() + ",")
                        .append(v.getStanica() + ",")
                        .append(v.getVodostaj() + ",")
                        .append(v.getTemperatura_zraka() + ",")
                        .append(v.getVlaznost() + ",")
                        .append(v.getTlak_zraka() + ",")
                        .append(v.getOborine() + ",")
                        .append(v.getStanje_vremena() + ",");

                if (v.getVjetar() != null) {
                    writer.append(v.getVjetar().getSmjer() + ",")
                            .append(v.getVjetar().getBrzina() + "\n");
                } else {
                    writer.append("N/A,N/A\n");
                }
            }

            writer.flush();
        }
    }


    public void generateJSON() throws IOException {
        List<Vodostaj> vodostaji = vodostajRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("vodostaji.json"), vodostaji);
    }
}
