package com.github.lunchee.writings.command.dictionary.converters;

import com.github.lunchee.writings.command.dictionary.Language;

import javax.persistence.Converter;
import javax.persistence.AttributeConverter;

@Converter(autoApply = true)
public class LanguageTypeConverter implements AttributeConverter<Language, String> {

    @Override
    public String convertToDatabaseColumn(Language language) {
        return language.getValueCode();
    }

    @Override
    public Language convertToEntityAttribute(String string) {
        return new Language(string);
    }
}
