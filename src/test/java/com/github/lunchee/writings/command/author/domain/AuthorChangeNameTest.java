package com.github.lunchee.writings.command.author.domain;

import com.github.lunchee.writings.command.dictionary.Language;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorChangeNameTest {

    @Test
    public void should_change_existing_name() {
        // given
        Author author = Author.create(
                AuthorName.create("Existing Name", NameType.ORIGINAL, new Language("EN")).get()
        ).get();
        AuthorNameEntity existingName = getName(author, "Existing Name");

        // when
        AuthorName newName = AuthorName.create("Neuer Name", NameType.TRANSLITERATION, new Language("DE")).get();
        author.changeName(existingName.getId(), newName);

        // then
        assertThat(author.getNames())
                .extracting(AuthorNameEntity::getId)
                .containsExactly(existingName.getId());
        assertThat(author.getNames())
                .extracting(AuthorNameEntity::getName)
                .containsExactly(newName);
    }

    @Test
    public void should_change_only_provided_name() {
        // given
        Author author = Author.create(
                AuthorName.create("Existing Name", NameType.ORIGINAL, new Language("EN")).get()
        ).get();
        author.addName(AuthorName.create("Another Name", NameType.ORIGINAL, new Language("EN")).get());

        AuthorNameEntity existingName = getName(author, "Existing Name");

        // when
        AuthorName newName = AuthorName.create("Neuer Name", NameType.TRANSLITERATION, new Language("DE")).get();
        author.changeName(existingName.getId(), newName);

        // then
        assertThat(author.getNames())
                .extracting(AuthorNameEntity::getId)
                .containsExactly(existingName.getId());
        assertThat(author.getNames())
                .extracting(AuthorNameEntity::getName)
                .containsExactly(newName);
    }

    private AuthorNameEntity getName(Author author, String value) {
        return author.getNames().stream()
                .filter(authorName -> authorName.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Unable to find Author name " + value));
    }
}
