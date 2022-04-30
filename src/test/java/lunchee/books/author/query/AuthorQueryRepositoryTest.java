package lunchee.books.author.query;

import lunchee.books.configuration.QueryTest;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.util.Optional;

import static lunchee.books.util.TestUtils.readResource;
import static org.assertj.core.api.Assertions.assertThat;

@QueryTest
class AuthorQueryRepositoryTest {

    @Autowired
    private Jdbi jdbi;

    @Test
    public void should_select_existing_author_by_id_with_all_fields_set() {
        Author existingAuthor = givenExistingAuthor(Author.builder()
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
        Author existingAuthor = givenExistingAuthor(Author.builder());

        Optional<Author> foundAuthor = jdbi.onDemand(AuthorQueryRepository.class)
                .findAuthorById(existingAuthor.getId());

        assertThat(foundAuthor).hasValueSatisfying(actual -> {
            assertThat(actual).hasAllNullFieldsOrPropertiesExcept("id");
            assertThat(actual.getId()).isEqualTo(existingAuthor.getId());
        });
    }

    @Test
    public void should_select_author_by_id() {
        givenExistingAuthor(Author.builder());

        Optional<Author> foundAuthor = jdbi.onDemand(AuthorQueryRepository.class)
                .findAuthorById(-1L);

        assertThat(foundAuthor).isEmpty();
    }

    // todo: tests for author name and author languages

    private Author givenExistingAuthor(Author.AuthorBuilder authorBuilder) {
        Author author = authorBuilder.build();
        long authorId = jdbi.withHandle(handle ->
                handle.createUpdate("insert into author(birth_year, death_year, whereabouts, photo) values (:birthYear, :deathYear, :whereabouts, :photo)")
                        .bindBean(author)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Long.class)
                        .one()
        );

        return authorBuilder.id(authorId).build();
    }
}