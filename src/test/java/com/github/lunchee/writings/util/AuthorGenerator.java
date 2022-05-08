package com.github.lunchee.writings.util;

import com.github.lunchee.writings.query.author.Author;
import com.github.lunchee.writings.query.author.AuthorLanguage;
import com.github.lunchee.writings.query.author.AuthorName;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.util.Arrays;
import java.util.List;

import static com.github.lunchee.writings.query.jooq.Tables.*;

@RequiredArgsConstructor
public class AuthorGenerator {

    private final DSLContext create;

    public Author givenExistingAuthor(Author.AuthorBuilder author) {
        return create.insertInto(AUTHOR)
                .set(create.newRecord(AUTHOR, author.build()))
                .returning()
                .fetchOne()
                .into(Author.class);
    }

    public List<AuthorName> givenExistingNames(Author author, AuthorName.AuthorNameBuilder... authorNames) {
        return Arrays.stream(authorNames)
                .map(authorName -> authorName.authorId(author.getId()))
                .map(this::insertAuthorName)
                .toList();
    }

    private AuthorName insertAuthorName(AuthorName.AuthorNameBuilder authorName) {
        return create.insertInto(AUTHOR_NAME)
                .set(create.newRecord(AUTHOR_NAME, authorName.build()))
                .returning()
                .fetchOne()
                .into(AuthorName.class);
    }

    public List<AuthorLanguage> givenExistingLanguages(Author author, AuthorLanguage.AuthorLanguageBuilder... authorLanguages) {
        return Arrays.stream(authorLanguages)
                .map(authorLanguage -> authorLanguage.authorId(author.getId()))
                .map(this::insertLAuthorLanguage)
                .toList();
    }

    private AuthorLanguage insertLAuthorLanguage(AuthorLanguage.AuthorLanguageBuilder authorLanguage) {
        return create.insertInto(AUTHOR_LANGUAGE)
                .set(create.newRecord(AUTHOR_LANGUAGE, authorLanguage.build()))
                .returning()
                .fetchOne()
                .into(AuthorLanguage.class);
    }
}
