package com.github.lunchee.writings.command.author.domain;

import io.vavr.control.Either;
import lombok.NoArgsConstructor;
import com.github.lunchee.writings.command.dictionary.Language;

import javax.annotation.Nonnull;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;
import static com.github.lunchee.writings.command.author.domain.AuthorError.LANGUAGE_NOT_FOUND;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class AuthorLanguages {

    @Transient
    private Author author;

    @OneToMany(mappedBy = "author", cascade = ALL, orphanRemoval = true)
    private final List<AuthorLanguage> languages = new ArrayList<>();

    public AuthorLanguages(@Nonnull Author author) {
        this.author = author;
    }

    public Either<AuthorError, AuthorLanguage> add(@Nonnull Language language) {
        return checkLanguageCanBeAdded(language)
                .map(addedLanguage -> new AuthorLanguage(author, addedLanguage, isAddedLanguageDefault()))
                .peek(languages::add);
    }

    private boolean isAddedLanguageDefault() {
        return languages.isEmpty();
    }

    private Either<AuthorError, Language> checkLanguageCanBeAdded(Language language) {
        return languages.stream().anyMatch(existingLanguage -> existingLanguage.getLanguage().equals(language))
                ? Either.left(AuthorError.LANGUAGE_OCCUPIED)
                : Either.right(language);
    }

    public Either<AuthorError, AuthorLanguage> setDefault(@Nonnull Long id) {
        return getById(id)
                .peek(found -> resetCurrentDefault())
                .peek(AuthorLanguage::setAsDefault);
    }

    private void resetCurrentDefault() {
        languages.stream()
                .filter(AuthorLanguage::isDefault)
                .forEach(AuthorLanguage::resetDefault);
    }

    private Either<AuthorError, AuthorLanguage> getById(Long id) {
        return languages.stream()
                .filter(language -> language.getId().equals(id))
                .map(Either::<AuthorError, AuthorLanguage>right)
                .findFirst()
                .orElseGet(() -> Either.left(LANGUAGE_NOT_FOUND));
    }

    public Either<AuthorError, AuthorLanguage> replace(@Nonnull Long existingId, @Nonnull Language newLanguage) {
        return checkLanguageCanBeAdded(newLanguage)
                .flatMap(ok -> getById(existingId))
                .peek(existingLanguage -> existingLanguage.setLanguage(newLanguage));
    }

    public Either<AuthorError, AuthorLanguage> remove(@Nonnull Long languageId) {
        return getById(languageId)
                .peek(languages::remove)
                .peek(AuthorLanguage::removeAuthor)
                .peek(removedLanguage -> {
                    if (removedLanguage.isDefault()) {
                        setFirstDefault();
                    }
                });
    }

    private void setFirstDefault() {
        languages.stream()
                .findFirst()
                .ifPresent(AuthorLanguage::setAsDefault);
    }
}
