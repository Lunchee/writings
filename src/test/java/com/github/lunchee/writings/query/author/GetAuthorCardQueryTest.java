package com.github.lunchee.writings.query.author;

import com.github.lunchee.writings.query.configuration.QueryTest;
import com.github.lunchee.writings.query.generator.QueryAuthorGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@QueryTest
public class GetAuthorCardQueryTest {

    @Autowired
    private GetAuthorCardQuery getAuthorCardQuery;
    @Autowired
    private QueryAuthorGenerator authorGenerator;

    @Test
    public void should_select_author_with_names_and_languages() {
        Author existingAuthor = authorGenerator.givenExistingAuthor(Author.builder()
                .birthYear(1899)
                .deathYear(1972)
                .whereabouts("Osaka, Japan"));
        List<AuthorName> existingNames = authorGenerator.givenExistingNames(existingAuthor,
                AuthorName.builder().name("川端 康成").language("JP").type("ORIGINAL"),
                AuthorName.builder().name("Кавабата Ясунари").language("RU").type("TRANSLITERATION"));
        List<AuthorLanguage> existingLanguages = authorGenerator.givenExistingLanguages(existingAuthor,
                AuthorLanguage.builder().language("JP").isDefault(true));

        Optional<AuthorCard> authorCard = getAuthorCardQuery.execute(existingAuthor.getId());

        assertThat(authorCard).contains(
                AuthorCard.builder()
                        .author(existingAuthor)
                        .names(existingNames)
                        .languages(existingLanguages)
                        .build()
        );
    }

    @Test
    public void should_select_author_without_names_or_languages() {
        Author existingAuthor = authorGenerator.givenExistingAuthor(Author.builder());

        Optional<AuthorCard> authorCard = getAuthorCardQuery.execute(existingAuthor.getId());

        assertThat(authorCard).contains(
                AuthorCard.builder()
                        .author(existingAuthor)
                        .names(emptyList())
                        .languages(emptyList())
                        .build()
        );
    }

    @Test
    public void should_return_empty_if_author_not_found() {
        Optional<AuthorCard> authorCard = getAuthorCardQuery.execute(-1L);

        assertThat(authorCard).isEmpty();
    }
}