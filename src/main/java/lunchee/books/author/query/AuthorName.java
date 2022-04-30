package lunchee.books.author.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class AuthorName {
    long id;
    String name;
    String type;
    String language;
}
