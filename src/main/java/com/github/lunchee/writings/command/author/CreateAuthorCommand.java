package com.github.lunchee.writings.command.author;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import com.github.lunchee.writings.command.author.domain.Author;
import com.github.lunchee.writings.command.author.domain.AuthorError;
import com.github.lunchee.writings.command.author.domain.AuthorName;
import com.github.lunchee.writings.command.author.domain.NameType;
import com.github.lunchee.writings.command.dictionary.Language;
import com.github.lunchee.writings.command.utility.Command;

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
