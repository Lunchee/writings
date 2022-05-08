package com.github.lunchee.writings.query.author;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthorName {
    Long id;
    long authorId;
    String name;
    String type;
    String language;
}
