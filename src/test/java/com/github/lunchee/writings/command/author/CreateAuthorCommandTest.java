package com.github.lunchee.writings.command.author;

import com.github.lunchee.writings.command.author.CreateAuthorCommand.CreateAuthorRequest;
import com.github.lunchee.writings.command.author.domain.AuthorError;
import com.github.lunchee.writings.command.author.domain.NameType;
import com.github.lunchee.writings.command.dictionary.Language;
import com.github.lunchee.writings.configuration.CommandTest;
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