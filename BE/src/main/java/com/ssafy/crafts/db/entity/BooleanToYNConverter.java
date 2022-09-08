package com.ssafy.crafts.db.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {
    /**
     * @작성자 : 김민주
     * @Method 설명 : Boolean 값을 Y 또는 N 으로 컨버트
     * @param attribute boolean 값
     * @return String true 인 경우 Y 또는 false 인 경우 N
     */
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    /**
     * @작성자 : 김민주
     * @Method 설명 : Y 또는 N 을 Boolean 으로 컨버트
     * @param yn Y 또는 N
     * @return Boolean 대소문자 상관없이 Y 인 경우 true, N 인 경우 false
     */
    @Override
    public Boolean convertToEntityAttribute(String yn) {
        return "Y".equalsIgnoreCase(yn);
    }
}
