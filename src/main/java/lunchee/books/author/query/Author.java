package lunchee.books.author.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class Author {
    long id;
    Integer birthYear;
    Integer deathYear;
    String whereabouts;
    byte[] photo;
}
