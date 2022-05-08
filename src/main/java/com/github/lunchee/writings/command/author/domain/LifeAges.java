package com.github.lunchee.writings.command.author.domain;

import io.vavr.control.Either;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
public class LifeAges {
    private Integer birthYear;
    private Integer deathYear;

    public Either<AuthorError, Void> setBirthYear(@Nullable Integer birthYear) {
        return checkLifeInterval(birthYear, this.deathYear)
                .peek(ok -> this.birthYear = birthYear);
    }

    private Either<AuthorError, Void> checkLifeInterval(Integer birthYear, Integer deathYear) {
        return birthYear != null && deathYear != null && birthYear > deathYear
                ? Either.left(AuthorError.BIRTH_YEAR_GREATER_THAN_DEATH_YEAR)
                : Either.right(null);
    }

    public Either<AuthorError, Void> setDeathYear(@Nullable Integer deathYear) {
        return checkLifeInterval(this.birthYear, deathYear)
                .peek(ok -> this.deathYear = deathYear);
    }
}
