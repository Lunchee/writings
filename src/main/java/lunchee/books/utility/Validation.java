package lunchee.books.utility;

import io.vavr.control.Either;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Validation {

    public static <E> Either<E, String> requireNotBlank(@Nullable String value, @Nonnull E error) {
        return (value == null || value.isBlank()) ? Either.left(error) : Either.right(value);
    }

    public static <T> T requireNotNull(@Nullable T value, @Nonnull String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException("'%s' must not be null".formatted(fieldName));
        }

        return value;
    }
}
