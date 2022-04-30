package lunchee.books.author.query;

import lombok.RequiredArgsConstructor;
import lunchee.books.utility.Query;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;

@Query
@RequiredArgsConstructor
public class GetAuthorCardQuery {

    private final Jdbi jdbi;

    public Optional<AuthorCard> execute(long authorId) {
        return jdbi.withHandle(handle -> {
            var authorRepository = handle.attach(AuthorQueryRepository.class);
            return authorRepository.findAuthorById(authorId)
                    .map(author ->
                            new AuthorCard(
                                    author,
                                    authorRepository.findAuthorNames(author),
                                    authorRepository.findAuthorLanguages(author)
                            ));
        });
    }
}
