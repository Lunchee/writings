package com.github.lunchee.writings.command.author;

import io.vavr.control.Either;
import com.github.lunchee.writings.command.author.domain.AuthorError;

public interface AuthorCommand<TResult> {

    Either<AuthorError, TResult> execute();
}
