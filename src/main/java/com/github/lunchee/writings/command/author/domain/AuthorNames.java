package com.github.lunchee.writings.command.author.domain;

import io.vavr.control.Either;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;
import static com.github.lunchee.writings.command.author.domain.NameType.ORIGINAL;
import static com.github.lunchee.writings.command.author.domain.NameType.TRANSLITERATION;
import static com.github.lunchee.writings.command.utility.Validation.requireNotNull;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class AuthorNames {

    @Transient
    private Author author;

    @OneToMany(mappedBy = "author", cascade = ALL, orphanRemoval = true)
    private final List<AuthorNameEntity> names = new ArrayList<>();

    public AuthorNames(@Nonnull Author author) {
        this.author = requireNotNull(author, "author");
    }

    public Either<AuthorError, AuthorNameEntity> add(@Nonnull AuthorName name) {
        return checkNameCanBeAdded(name)
                .map(ok -> new AuthorNameEntity(author, name))
                .peek(names::add);
    }

    private Either<AuthorError, AuthorName> checkNameCanBeAdded(AuthorName newName) {
        return checkNameAvailable(newName)
                .flatMap(this::checkLanguage);
    }

    private Either<AuthorError, AuthorName> checkNameAvailable(AuthorName newName) {
        return names.stream().anyMatch(existingName -> existingName.getName().equals(newName))
                ? Either.left(AuthorError.NAME_OCCUPIED)
                : Either.right(newName);
    }

    private Either<AuthorError, AuthorName> checkLanguage(AuthorName newName) {
        return names.stream().anyMatch(name -> isTransliterationLanguageEqualsOriginalLanguage(newName, name))
                ? Either.left(AuthorError.TRANSLITERATION_LANGUAGE_EQUALS_ORIGINAL)
                : Either.right(newName);
    }

    private boolean isTransliterationLanguageEqualsOriginalLanguage(AuthorName newName, AuthorNameEntity name) {
        return name.getLanguage().equals(newName.getLanguage())
                && name.getNameType() == (newName.isOriginal() ? TRANSLITERATION : ORIGINAL);
    }

    public Either<AuthorError, AuthorNameEntity> change(@Nonnull Long id, @Nonnull AuthorName newName) {
        Either<AuthorError, AuthorNameEntity> nameToChange = getNameById(id);
        if (nameToChange.isRight()) {
            return nameToChange.map(names::remove)
                    .flatMap(removed -> add(newName))
                    .peekLeft(error -> names.add(nameToChange.get()));
        } else {
            return nameToChange;
        }
    }

    private Either<AuthorError, AuthorNameEntity> getNameById(Long id) {
        return names.stream()
                .filter(name -> name.getId().equals(id))
                .findFirst()
                .map(Either::<AuthorError, AuthorNameEntity>right)
                .orElseGet(() -> Either.left(AuthorError.NAME_NOT_FOUND));
    }

    public Either<AuthorError, AuthorNameEntity> remove(@Nonnull Long id) {
        return getNameById(id)
                .peek(names::remove)
                .peek(AuthorNameEntity::removeAuthor);
    }

    public List<AuthorNameEntity> getNames() {
        return unmodifiableList(names);
    }
}
