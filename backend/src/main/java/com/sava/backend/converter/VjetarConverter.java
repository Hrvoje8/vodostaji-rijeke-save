package com.sava.backend.converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sava.backend.model.Vjetar;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class VjetarConverter implements AttributeConverter<Vjetar, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Vjetar vjetar) {
        try {
            return objectMapper.writeValueAsString(vjetar);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // handle error properly in a production setting
        }
    }

    @Override
    public Vjetar convertToEntityAttribute(String vjetarJson) {
        try {
            return objectMapper.readValue(vjetarJson, Vjetar.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null; // handle error properly in a production setting
        }
    }
}