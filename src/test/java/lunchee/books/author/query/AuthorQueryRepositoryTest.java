package lunchee.books.author.query;

import lunchee.books.configuration.QueryTest;
import lunchee.books.util.AuthorGenerator;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static lunchee.books.author.command.domain.NameType.ORIGINAL;
import static lunchee.books.author.command.domain.NameType.TRANSLITERATION;
import static lunchee.books.util.TestUtils.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@QueryTest
class AuthorQueryRepositoryTest {

    @Autowired
    private Jdbi jdbi;
    @Autowired
    private AuthorGenerator authorGenerator;

    @Test
    public void should_select_existing_author_by_id_with_all_fields_set() {
        Author existingAuthor = authorGenerator.givenExistingAuthor(Author.builder()
                .birthYear(1799)
                .deathYear(1837)
                .whereabouts("Saint-Petersburg, Russian Empire")
                .photo(readResource(Path.of("author", "pushkin.jpg"))));

        Optional<Author> foundAuthor = jdbi.onDemand(AuthorQueryRepository.class)
                .findAuthorById(existingAuthor.getId());

        assertThat(foundAuthor).hasValueSatisfying(actual -> {
            assertThat(actual).hasNoNullFieldsOrProperties();

            assertThat(actual.getId()).isEqualTo(existingAuthor.getId());
            assertThat(actual.getBirthYear()).isEqualTo(existingAuthor.getBirthYear());
            assertThat(actual.getDeathYear()).isEqualTo(existingAuthor.getDeathYear());
            assertThat(actual.getWhereabouts()).isEqualTo(existingAuthor.getWhereabouts());
            assertThat(actual.getPhoto()).containsExactly(existingAuthor.getPhoto());
        });
    }

    @Test
    public void should_select_existing_author_by_id_with_mandatory_fields_only() {
        Author existingAuthor = authorGenerator.givenExistingAuthor(Author.builder());

        Optional<Author> foundAuthor = jdbi.onDemand(AuthorQueryRepository.class)
                .findAuthorById(existingAuthor.getId());

        assertThat(foundAuthor).hasValueSatisfying(actual -> {
            assertThat(actual).hasAllNullFieldsOrPropertiesExcept("id");
            assertThat(actual.getId()).isEqualTo(existingAuthor.getId());
        });
    }

    @Test
    public void should_select_author_by_id() {
        authorGenerator.givenExistingAuthor(Author.builder());

        Optional<Author> foundAuthor = jdbi.onDemand(AuthorQueryRepository.class)
                .findAuthorById(-1L);

        assertThat(foundAuthor).isEmpty();
    }

    @Test
    public void should_select_author_names() {
        Author existingAuthor = authorGenerator.givenExistingAuthor(Author.builder());
        List<AuthorName> existingNames = authorGenerator.givenExistingNames(
                existingAuthor,
                AuthorName.builder().name("Пушкин").language("RU").type(ORIGINAL.name()),
                AuthorName.builder().name("Pushkin").language("EN").type(TRANSLITERATION.name())
        );

        List<AuthorName> foundNames = jdbi.onDemand(AuthorQueryRepository.class)
                .findAuthorNames(existingAuthor);

        assertThat(foundNames).isEqualTo(existingNames);
    }

    @Test
    public void should_select_empty_author_names() {
        Author existingAuthor = authorGenerator.givenExistingAuthor(Author.builder());

        List<AuthorName> foundNames = jdbi.onDemand(AuthorQueryRepository.class)
                .findAuthorNames(existingAuthor);

        assertThat(foundNames).isEmpty();
    }

    @Test
    public void should_select_author_languages() {
        Author existingAuthor = authorGenerator.givenExistingAuthor(Author.builder());
        List<AuthorLanguage> existingLanguages = authorGenerator.givenExistingLanguages(
                existingAuthor,
                AuthorLanguage.builder().language("RU").isDefault(true),
                AuthorLanguage.builder().language("EN").isDefault(false));

        List<AuthorLanguage> foundLanguages = jdbi.onDemand(AuthorQueryRepository.class)
                .findAuthorLanguages(existingAuthor);

        assertThat(foundLanguages).isEqualTo(existingLanguages);
    }

    @Test
    public void should_select_empty_author_languages() {
        Author existingAuthor = authorGenerator.givenExistingAuthor(Author.builder());

        List<AuthorLanguage> foundLanguages = jdbi.onDemand(AuthorQueryRepository.class)
                .findAuthorLanguages(existingAuthor);

        assertThat(foundLanguages).isEmpty();
    }


}