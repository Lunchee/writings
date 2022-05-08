package com.github.lunchee.writings.command.author.domain;

import io.vavr.control.Either;
import com.github.lunchee.writings.command.dictionary.Language;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorCreateTest {

    @Test
    public void should_create_author_with_non_blank_original_name() {
        AuthorName authorName = AuthorName.original("Pichu", new Language("EN")).get();

        Either<AuthorError, Author> author = Author.create(authorName);

        VavrAssertions.assertThat(author).hasRightValueSatisfying(it ->
                assertThat(it.getNames())
                        .extracting(AuthorNameEntity::getName)
                        .containsExactly(authorName)
        );
    }
}
