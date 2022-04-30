package lunchee.books.author.query;

import java.util.List;

public record AuthorCard(
        Author author,
        List<AuthorName> names,
        List<AuthorLanguage> languages
) {
}
