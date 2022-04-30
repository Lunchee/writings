package lunchee.books.author.command;

import lunchee.books.author.command.CreateAuthorCommand.CreateAuthorRequest;
import lunchee.books.author.command.domain.AuthorError;
import lunchee.books.author.command.domain.NameType;
import lunchee.books.configuration.CommandTest;
import lunchee.books.dictionary.Language;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@CommandTest
class CreateAuthorCommandTest {

    @Autowired
    private CreateAuthorCommand createAuthorCommand;

    @Test
    public void should_return_author_id_in_successful_case() {
        var result = createAuthorCommand.execute(new CreateAuthorRequest("Pichu", NameType.ORIGINAL, new Language("EN")));

        VavrAssertions.assertThat(result).hasRightValueSatisfying(it ->
                assertThat(it.authorId()).isNotNull());
    }

    @Test
    public void should_return_error_in_case_of_failure() {
        var result = createAuthorCommand.execute(new CreateAuthorRequest(" ", NameType.ORIGINAL, new Language("EN")));

        VavrAssertions.assertThat(result).containsOnLeft(AuthorError.NAME_IS_EMPTY);
    }
}