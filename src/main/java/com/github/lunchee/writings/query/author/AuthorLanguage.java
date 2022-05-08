package com.github.lunchee.writings.query.author;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class AuthorLanguage {
    Long id;
    long authorId;
    String language;
    boolean isDefault;
}
