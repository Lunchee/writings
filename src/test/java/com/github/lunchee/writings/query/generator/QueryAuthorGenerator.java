package com.github.lunchee.writings.query.generator;

import com.github.lunchee.writings.query.author.Author;
import com.github.lunchee.writings.query.author.AuthorLanguage;
import com.github.lunchee.writings.query.author.AuthorName;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.lunchee.writings.query.jooq.Tables.*;

@RequiredArgsConstructor
public class QueryAuthorGenerator {

    private final DSLContext create;

    public Author givenExistingAuthor(Author.AuthorBuilder author) {
        return create.insertInto(AUTHOR)
                .set(create.newRecord(AUTHOR, author.build()))
                .returning()
                .fetchOne()
                .into(Author.class);
    }

    public List<AuthorName> givenExistingNames(Author author, AuthorName.AuthorNameBuilder... authorNames) {
        AtomicInteger nameOrder = new AtomicInteger();
        return Arrays.stream(authorNames)
                .map(it -> it.authorId(author.getId()))
                .map(it -> it.nameOrder(nameOrder.getAndIncrement()))
                .map(this::insertAuthorName)
                .toList();
    }

    private AuthorName insertAuthorName(AuthorName.AuthorNameBuilder authorName) {
        create.insertInto(AUTHOR_NAME)
                .set(create.newRecord(AUTHOR_NAME, authorName.build()))
                .execute();

        return authorName.build();
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
