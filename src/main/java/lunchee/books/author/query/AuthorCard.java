package lunchee.books.author.query;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AuthorCard {
    Author author;
    List<AuthorName> names;
    List<AuthorLanguage> languages;
}
