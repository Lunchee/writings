package com.github.lunchee.writings.command.generator;

import com.github.lunchee.writings.command.author.domain.Author;
import com.github.lunchee.writings.command.author.domain.AuthorError;
import com.github.lunchee.writings.command.author.domain.AuthorName;
import io.vavr.control.Either;

import java.util.Arrays;

public class AuthorGenerator {

    public static Author createAuthor(AuthorName... names) {
        Author author = Author.create(names[0]).get();
        if (names.length > 1) {
            for (int nameIndex = 1; nameIndex < names.length; ++nameIndex) {
                author.addName(names[nameIndex]).get();
            }
        }

        return author;
    }

    @SafeVarargs
    public static Author createAuthor(Either<AuthorError, AuthorName>... names) {
        return createAuthor(
                Arrays.stream(names)
                        .map(Either::get)
                        .toArray(AuthorName[]::new)
        );
    }
}
