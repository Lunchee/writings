package com.github.lunchee.writings.command.author.domain;

import com.github.lunchee.writings.command.dictionary.Language;
import io.vavr.control.Either;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.OrderColumn;
import java.util.ArrayList;
import java.util.List;

import static com.github.lunchee.writings.command.author.domain.NameType.ORIGINAL;
import static com.github.lunchee.writings.command.author.domain.NameType.TRANSLITERATION;
import static java.util.Collections.unmodifiableList;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class AuthorNames {

    @ElementCollection
    @CollectionTable(name = "author_name")
    @OrderColumn(name = "name_order")
    private final List<AuthorName> names = new ArrayList<>();

    public Either<AuthorError, AuthorName> add(@Nonnull AuthorName name) {
        return checkNameCanBeAdded(name)
                .peek(names::add);
    }

    private Either<AuthorError, AuthorName> checkNameCanBeAdded(AuthorName newName) {
        return checkNameAvailable(newName)
                .flatMap(this::checkLanguage);
    }

    private Either<AuthorError, AuthorName> checkNameAvailable(AuthorName newName) {
        return names.stream()
                .anyMatch(existingName -> existingName.equals(newName))
                ? Either.left(AuthorError.NAME_OCCUPIED)
                : Either.right(newName);
    }

    private Either<AuthorError, AuthorName> checkLanguage(AuthorName newName) {
        return names.stream().anyMatch(name -> isTransliterationLanguageEqualsOriginalLanguage(newName, name))
                ? Either.left(AuthorError.TRANSLITERATION_LANGUAGE_EQUALS_ORIGINAL)
                : Either.right(newName);
    }

    private boolean isTransliterationLanguageEqualsOriginalLanguage(AuthorName newName, AuthorName name) {
        return name.getLanguage().equals(newName.getLanguage())
                && name.getType() == (newName.isOriginal() ? TRANSLITERATION : ORIGINAL);
    }

    public Either<AuthorError, AuthorName> change(int nameOrder, @Nonnull AuthorName newName) {
        Either<AuthorError, AuthorName> existingName = getNameByOrder(nameOrder);
        if (existingName.isRight()) {
            names.set(nameOrder, new DeletedAuthorName());
            return set(nameOrder, newName)
                    .peekLeft(error -> names.set(nameOrder, existingName.get()));
        } else {
            return existingName;
        }
    }

    private Either<AuthorError, AuthorName> set(int position, AuthorName name) {
        return checkNameCanBeAdded(name)
                .peek(ok -> names.set(position, name));
    }

    private Either<AuthorError, AuthorName> getNameByOrder(int nameOrder) {
        try {
            return Either.right(names.get(nameOrder));
        } catch (IndexOutOfBoundsException exception) {
            return Either.left(AuthorError.NAME_NOT_FOUND);
        }
    }

    public Either<AuthorError, AuthorName> remove(int nameOrder) {
        return getNameByOrder(nameOrder)
                .peek(names::remove);
    }

    public List<AuthorName> getNames() {
        return unmodifiableList(names);
    }

    private static class DeletedAuthorName extends AuthorName {

        public DeletedAuthorName() {
            super("DELETED", TRANSLITERATION, new Language("DELETED"));
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
