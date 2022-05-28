package com.github.lunchee.writings.command.author.domain;

import com.github.lunchee.writings.command.dictionary.Language;
import io.vavr.control.Either;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;

import static com.github.lunchee.writings.command.generator.AuthorGenerator.createAuthor;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorChangeNameTest {

    @Test
    public void should_change_existing_name() {
        // given
        var existingName = AuthorName.create("Existing Name", NameType.ORIGINAL, new Language("EN")).get();
        Author author = Author.create(existingName).get();

        // when
        AuthorName newName = AuthorName.create("Neuer Name", NameType.TRANSLITERATION, new Language("DE")).get();
        author.changeName(0, newName);

        // then
        assertThat(author.getNames()).containsExactly(newName);
    }

    @Test
    public void should_change_only_requested_name() {
        // given
        var firstName = AuthorName.create("Existing Name", NameType.ORIGINAL, new Language("EN")).get();
        var secondName = AuthorName.create("Another Name", NameType.ORIGINAL, new Language("EN")).get();
        Author author = createAuthor(firstName, secondName);

        // when
        var newFirstName = AuthorName.create("Neuer Name", NameType.TRANSLITERATION, new Language("DE")).get();
        author.changeName(0, newFirstName);

        // then
        assertThat(author.getNames()).containsExactly(newFirstName, secondName);
    }

    @Test
    public void should_return_error_if_not_existent_name_changed() {
        // given
        Author author = createAuthor(AuthorName.original("Existing Name", new Language("EN")));

        // when
        Either<AuthorError, Author> changeNameResult =
                AuthorName.create("Neuer Name", NameType.TRANSLITERATION, new Language("DE"))
                        .flatMap(it -> author.changeName(1, it));

        // then
        VavrAssertions.assertThat(changeNameResult).containsOnLeft(AuthorError.NAME_NOT_FOUND);
    }

    @Test
    public void should_check_name_constraints_cannot_change_to_duplicate_name() {
        // given
        Author author = createAuthor(
                AuthorName.original("Original Name", new Language("EN")),
                AuthorName.transliteration("Transliteration Name", new Language("DE"))
        );

        // when
        Either<AuthorError, Author> changeNameResult =
                AuthorName.original("Original Name", new Language("EN"))
                        .flatMap(it -> author.changeName(1, it));

        // then
        VavrAssertions.assertThat(changeNameResult).containsOnLeft(AuthorError.NAME_OCCUPIED);
    }

    @Test
    public void should_check_name_constraints_transliteration_should_have_different_language_than_original() {
        // given
        Author author = createAuthor(
                AuthorName.original("Original Name", new Language("EN")),
                AuthorName.transliteration("Transliteration Name", new Language("DE"))
        );

        // when
        Either<AuthorError, Author> changeNameResult =
                AuthorName.transliteration("Transliteration Name", new Language("EN"))
                        .flatMap(it -> author.changeName(1, it));

        // then
        VavrAssertions.assertThat(changeNameResult).containsOnLeft(AuthorError.TRANSLITERATION_LANGUAGE_EQUALS_ORIGINAL);
    }
}
