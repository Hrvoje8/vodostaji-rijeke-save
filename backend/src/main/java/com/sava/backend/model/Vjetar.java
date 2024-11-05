package com.sava.backend.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Vjetar {
    private String smjer;
    private float brzina;
}
