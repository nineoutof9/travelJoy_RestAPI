package com.ict.traveljoy.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PermissionToNumberConverter implements AttributeConverter<String, Integer> {

	@Override
	public Integer convertToDatabaseColumn(String attribute) {
		if (attribute == null) return null;

        switch (attribute) {
            case "ROLE_USER":
                return 1;
            case "ROLE_ADMIN":
                return 2;
            default:
                return 0; // 기본 권한
        }
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) {
		if (dbData == null) return null;

        switch (dbData) {
            case 1:
                return "ROLE_USER";
            case 2:
                return "ROLE_ADMIN";
            default:
                return "ROLE_GUEST"; // 기본 권한
        }
	}
	
}
