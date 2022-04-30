package lunchee.books.author.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class AuthorLanguage {
    long id;
    String language;
    boolean isDefault;
}
