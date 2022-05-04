package lunchee.books.util;

import lombok.RequiredArgsConstructor;
import lunchee.books.author.query.Author;
import lunchee.books.author.query.AuthorLanguage;
import lunchee.books.author.query.AuthorName;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AuthorGenerator {

    private final Jdbi jdbi;

    public Author givenExistingAuthor(Author.AuthorBuilder authorBuilder) {
        Author author = authorBuilder.build();
        long authorId = jdbi.withHandle(handle ->
                handle.createUpdate("insert into author (birth_year, death_year, whereabouts, photo) values (:birthYear, :deathYear, :whereabouts, :photo)")
                        .bindBean(author)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Long.class)
                        .one()
        );

        return authorBuilder.id(authorId).build();
    }

    public List<AuthorName> givenExistingNames(Author author, AuthorName.AuthorNameBuilder... authorNames) {
        return jdbi.withHandle(handle ->
                Arrays.stream(authorNames)
                        .map(authorName -> insertAuthorName(author, authorName, handle))
                        .toList());
    }

    private AuthorName insertAuthorName(Author author, AuthorName.AuthorNameBuilder authorName, Handle handle) {
        long nameId = handle.createUpdate("insert into author_name (author_id, name, type, language) values(:authorId, :name, :type, :language)")
                .bind("authorId", author.getId())
                .bindBean(authorName.build())
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Long.class)
                .one();

        return authorName.id(nameId).build();
    }

    public List<AuthorLanguage> givenExistingLanguages(Author author, AuthorLanguage.AuthorLanguageBuilder... authorLanguages) {
        return jdbi.withHandle(handle ->
                Arrays.stream(authorLanguages)
                        .map(language -> insertLAuthorLanguage(author, language, handle))
                        .toList());
    }

    private AuthorLanguage insertLAuthorLanguage(Author author, AuthorLanguage.AuthorLanguageBuilder language, Handle handle) {
        long languageId = handle.createUpdate("insert into author_language (author_id, language, is_default) values (:authorId, :language, :default)")
                .bind("authorId", author.getId())
                .bindBean(language.build())
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Long.class)
                .one();

        return language.id(languageId).build();
    }
}
