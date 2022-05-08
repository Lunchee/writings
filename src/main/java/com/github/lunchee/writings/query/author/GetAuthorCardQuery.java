package com.github.lunchee.writings.query.author;

import com.github.lunchee.writings.query.configuration.Query;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Query
@RequiredArgsConstructor
public class GetAuthorCardQuery {

    private final AuthorQueryRepository authorRepository;

    public Optional<AuthorCard> execute(long authorId) {
        return authorRepository.findAuthorById(authorId)
                .map(author ->
                        new AuthorCard(
                                author,
                                authorRepository.findAuthorNamesByAuthorId(authorId),
                                authorRepository.findAuthorLanguagesByAuthorId(authorId)
                        ));
    }
}
