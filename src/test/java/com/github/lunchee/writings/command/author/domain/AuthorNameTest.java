package com.github.lunchee.writings.command.author.domain;

import com.github.lunchee.writings.command.dictionary.Language;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class AuthorNameTest {

    @ParameterizedTest
    @MethodSource("blankNames")
    public void name_cannot_be_blank(String blankName) {
        assertThat(AuthorName.create(blankName, NameType.ORIGINAL, new Language("EN")))
                .containsOnLeft(AuthorError.NAME_IS_EMPTY);
    }

    private static Stream<String> blankNames() {
        return Stream.of(" ", null, "\t", "\n", "");
    }

    @Test
    public void name_should_contain_visible_characters() {
        assertThat(AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")))
                .hasRightValueSatisfying(name -> {
                    Assertions.assertThat(name.getValue()).isEqualTo("Pichu");
                    Assertions.assertThat(name.getType()).isEqualTo(NameType.ORIGINAL);
                    Assertions.assertThat(name.getLanguage()).isEqualTo(new Language("EN"));
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {" Name", "Name ", " Name ", "\tName\n"})
    public void name_is_trimmed(String nameWithBlanks) {
        assertThat(AuthorName.create(nameWithBlanks, NameType.ORIGINAL, new Language("EN")))
                .hasRightValueSatisfying(name ->
                        Assertions.assertThat(name.getValue()).isEqualTo("Name"));
    }

    @Test
    public void names_are_compared_case_insensitive() {
        assertThat(AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")))
                .isEqualTo(AuthorName.create("pichu", NameType.ORIGINAL, new Language("EN")));
    }

    @Test
    public void names_may_differ_by_value() {
        assertThat(AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")))
                .isNotEqualTo(AuthorName.create("Pecha", NameType.ORIGINAL, new Language("EN")));
    }

    @Test
    public void names_may_differ_by_type() {
        assertThat(AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")))
                .isNotEqualTo(AuthorName.create("Pichu", NameType.TRANSLITERATION, new Language("EN")));
    }

    @Test
    public void names_may_differ_by_language() {
        assertThat(AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")))
                .isNotEqualTo(AuthorName.create("Pichu", NameType.ORIGINAL, new Language("DE")));
    }
}
