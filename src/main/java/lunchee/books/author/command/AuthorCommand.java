package lunchee.books.author.command;

import io.vavr.control.Either;
import lunchee.books.author.command.domain.AuthorError;

public interface AuthorCommand<TResult> {

    Either<AuthorError, TResult> execute();
}
