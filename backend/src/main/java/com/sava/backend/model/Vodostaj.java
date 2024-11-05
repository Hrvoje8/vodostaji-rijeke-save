package com.sava.backend.model;

import com.sava.backend.converter.VjetarConverter;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "vodostaji")
public class Vodostaj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String datum;
    private String vrijeme;
    private String stanica;

    private float vodostaj;

    private float temperatura_zraka;

    private int vlaznost;

    private int tlak_zraka;

    private float oborine;

    private String stanje_vremena;

    @Convert(converter = VjetarConverter.class)
    private Vjetar vjetar;
}
