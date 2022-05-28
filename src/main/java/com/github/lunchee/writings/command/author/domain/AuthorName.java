package com.github.lunchee.writings.command.author.domain;

import com.github.lunchee.writings.command.dictionary.Language;
import io.vavr.control.Either;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

import static com.github.lunchee.writings.command.author.domain.NameType.ORIGINAL;
import static com.github.lunchee.writings.command.author.domain.NameType.TRANSLITERATION;
import static com.github.lunchee.writings.command.utility.Validation.requireNotNull;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@ToString
public class AuthorName {

    @Getter
    @Column(name = "name")
    private String value;

    @Getter
    @Enumerated(EnumType.STRING)
    private NameType type;

    @Getter
    private Language language;

    protected AuthorName(String value, NameType type, Language language) {
        this.value = requireNotNull(value, "value").strip();
        this.type = requireNotNull(type, "nameType");
        this.language = requireNotNull(language, "language");
    }

    public static Either<AuthorError, AuthorName> create(
            @Nonnull String name,
            @Nonnull NameType nameType,
            @Nonnull Language language
    ) {
        return checkValueNotBlank(name)
                .map(ok -> new AuthorName(name, nameType, language));
    }

    private static Either<AuthorError, String> checkValueNotBlank(String name) {
        return name == null || name.isBlank()
                ? Either.left(AuthorError.NAME_IS_EMPTY)
                : Either.right(name);
    }

    public static Either<AuthorError, AuthorName> original(@Nonnull String name, @Nonnull Language language) {
        return create(name, ORIGINAL, language);
    }

    public static Either<AuthorError, AuthorName> transliteration(@Nonnull String name, @Nonnull Language language) {
        return create(name, NameType.TRANSLITERATION, language);
    }

    public boolean isOriginal() {
        return type == ORIGINAL;
    }

    public boolean isTransliteration() {
        return type == TRANSLITERATION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorName that = (AuthorName) o;
        return value.equalsIgnoreCase(that.value)
                && type == that.type
                && language.equals(that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase(), type, language);
    }
}
