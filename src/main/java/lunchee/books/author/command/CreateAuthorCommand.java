package lunchee.books.author.command;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lunchee.books.author.command.domain.Author;
import lunchee.books.author.command.domain.AuthorError;
import lunchee.books.author.command.domain.AuthorName;
import lunchee.books.author.command.domain.NameType;
import lunchee.books.dictionary.Language;
import lunchee.books.utility.Command;

@Command
@RequiredArgsConstructor
public class CreateAuthorCommand {

    private final AuthorCommandRepository repository;

    public Either<AuthorError, CreateAuthorResponse> execute(CreateAuthorRequest request) {
        return AuthorName.create(request.name, request.type, request.language)
                .flatMap(Author::create)
                .map(repository::save)
                .map(Author::getId)
                .map(CreateAuthorResponse::new);
    }

    public record CreateAuthorRequest(String name, NameType type, Language language) {
    }

    public record CreateAuthorResponse(Long authorId) {
    }
}
