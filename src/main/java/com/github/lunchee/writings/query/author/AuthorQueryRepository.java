package com.github.lunchee.writings.query.author;

import com.github.lunchee.writings.query.configuration.QueryRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

import static com.github.lunchee.writings.query.jooq.Tables.*;

@QueryRepository
@RequiredArgsConstructor
public class AuthorQueryRepository {

    private final DSLContext create;

    public Optional<Author> findAuthorById(long id) {
        return create.select()
                .from(AUTHOR)
                .where(AUTHOR.ID.eq(id))
                .fetchOptionalInto(Author.class);
    }

    public List<AuthorName> findAuthorNamesByAuthorId(long authorId) {
        return create.select()
                .from(AUTHOR_NAME)
                .where(AUTHOR_NAME.AUTHOR_ID.eq(authorId))
                .fetchInto(AuthorName.class);
    }

    public List<AuthorLanguage> findAuthorLanguagesByAuthorId(long authorId) {
        return create.select()
                .from(AUTHOR_LANGUAGE)
                .where(AUTHOR_LANGUAGE.AUTHOR_ID.eq(authorId))
                .fetchInto(AuthorLanguage.class);
    }
}
