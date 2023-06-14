package com.example.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SensitiveConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        // TODO: 加密逻辑
        return null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        // TODO: 解密逻辑
        return null;
    }
}

