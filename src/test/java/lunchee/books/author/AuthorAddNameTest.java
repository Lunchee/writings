package lunchee.books.author;

import lunchee.books.author.command.domain.*;
import lunchee.books.dictionary.Language;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorAddNameTest {

    @Test
    public void author_may_have_original_and_transliteration_names_of_different_languages() {
        var existingName = AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")).get();
        var author = Author.create(existingName).get();

        var newName = AuthorName.create("Пичу", NameType.TRANSLITERATION, new Language("RU")).get();
        VavrAssertions.assertThat(author.addName(newName))
                .isRight();

        assertThat(author.getNames())
                .extracting(AuthorNameEntity::getName)
                .containsExactlyInAnyOrder(existingName, newName);
    }

    @Test
    public void should_not_add_duplicate_name_comparing_case_insensitive() {
        var existingName = AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")).get();
        var author = Author.create(existingName).get();

        var duplicatingName = AuthorName.create("pichu", NameType.ORIGINAL, new Language("EN")).get();
        VavrAssertions.assertThat(author.addName(duplicatingName))
                .containsOnLeft(AuthorError.NAME_OCCUPIED);

        assertThat(author.getNames())
                .extracting(AuthorNameEntity::getName)
                .containsOnly(existingName);
    }

    @Test
    public void author_may_have_several_original_names_of_same_language() {
        var firstOriginalName = AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")).get();
        var secondOriginalName = AuthorName.create("Pupichu", NameType.ORIGINAL, new Language("EN")).get();

        var author = Author.create(firstOriginalName)
                .flatMap(it -> it.addName(secondOriginalName));

        VavrAssertions.assertThat(author).hasRightValueSatisfying(it ->
                assertThat(it.getNames())
                        .extracting(AuthorNameEntity::getName)
                        .containsExactlyInAnyOrder(firstOriginalName, secondOriginalName));
    }

    @Test
    public void original_name_languages_may_differ() {
        var englishOriginalName = AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")).get();
        var russianOriginalName = AuthorName.create("Пичу", NameType.ORIGINAL, new Language("RU")).get();

        var author = Author.create(englishOriginalName)
                .flatMap(it -> it.addName(russianOriginalName));

        VavrAssertions.assertThat(author).hasRightValueSatisfying(it ->
                assertThat(it.getNames())
                        .extracting(AuthorNameEntity::getName)
                        .containsExactlyInAnyOrder(englishOriginalName, russianOriginalName));
    }

    @Test
    public void should_not_add_transliteration_when_author_has_original_name_of_same_language() {
        var originalName = AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")).get();
        var transliterationName = AuthorName.create("Peechu", NameType.TRANSLITERATION, new Language("EN")).get();

        var author = Author.create(originalName)
                .flatMap(it -> it.addName(transliterationName));

        VavrAssertions.assertThat(author).containsOnLeft(AuthorError.TRANSLITERATION_LANGUAGE_EQUALS_ORIGINAL);
    }

    @Test
    public void should_not_add_original_name_when_author_has_transliteration_of_same_language() {
        var transliterationName = AuthorName.create("Peechu", NameType.TRANSLITERATION, new Language("EN")).get();
        var originalName = AuthorName.create("Pichu", NameType.ORIGINAL, new Language("EN")).get();

        var author = Author.create(transliterationName)
                .flatMap(it -> it.addName(originalName));

        VavrAssertions.assertThat(author).containsOnLeft(AuthorError.TRANSLITERATION_LANGUAGE_EQUALS_ORIGINAL);
    }

    @Test
    public void author_may_miss_original_name() {
        var firstTransliterationName = AuthorName.create("Peechu", NameType.TRANSLITERATION, new Language("EN")).get();
        var secondTransliterationName = AuthorName.create("Пичу", NameType.TRANSLITERATION, new Language("RU")).get();

        var author = Author.create(firstTransliterationName)
                .flatMap(it -> it.addName(secondTransliterationName));

        VavrAssertions.assertThat(author).hasRightValueSatisfying(it ->
                assertThat(it.getNames())
                        .extracting(AuthorNameEntity::getName)
                        .containsExactlyInAnyOrder(firstTransliterationName, secondTransliterationName));
    }

    @Test
    public void author_may_have_several_transliteration_names_of_same_language() {
        var firstTransliterationName = AuthorName.create("Peechu", NameType.TRANSLITERATION, new Language("EN")).get();
        var secondTransliterationName = AuthorName.create("Pichu", NameType.TRANSLITERATION, new Language("EN")).get();

        var author = Author.create(firstTransliterationName)
                .flatMap(it -> it.addName(secondTransliterationName));

        VavrAssertions.assertThat(author).hasRightValueSatisfying(it ->
                assertThat(it.getNames())
                        .extracting(AuthorNameEntity::getName)
                        .containsExactlyInAnyOrder(firstTransliterationName, secondTransliterationName));
    }
}