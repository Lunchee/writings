package com.github.lunchee.writings.command.author.domain;

public enum AuthorError {
    NAME_IS_EMPTY,
    NAME_NOT_FOUND,
    NAME_OCCUPIED,
    TRANSLITERATION_LANGUAGE_EQUALS_ORIGINAL,
    BIRTH_YEAR_GREATER_THAN_DEATH_YEAR,
    LANGUAGE_OCCUPIED,
    LANGUAGE_NOT_FOUND
}
