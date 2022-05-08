package com.github.lunchee.writings.query.author;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Author {
    Long id;
    Integer birthYear;
    Integer deathYear;
    String whereabouts;
    byte[] photo;
}
