package com.github.lunchee.writings.command.author.domain;

import com.github.lunchee.writings.command.dictionary.Language;
import io.vavr.control.Either;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Author {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Getter
    private Long id;

    @Embedded
    private final AuthorNames names = new AuthorNames();

    @Embedded
    private final LifeAges lifeAges = new LifeAges();

    @Embedded
    private final AuthorLanguages languages = new AuthorLanguages(this);

    private String whereabouts;

    @Lob
    @Basic(fetch = LAZY)
    private byte[] photo;

    public static Either<AuthorError, Author> create(@Nonnull AuthorName name) {
        var author = new Author();
        return author.addName(name)
                .map(ok -> author);
    }

    public final Either<AuthorError, Author> addName(@Nonnull AuthorName name) {
        return names.add(name)
                .map(ok -> this);
    }

    public Either<AuthorError, Author> changeName(int nameOrder, @Nonnull AuthorName newName) {
        return names.change(nameOrder, newName)
                .map(ok -> this);
    }

    public Either<AuthorError, Author> removeName(int nameOrder) {
        return names.remove(nameOrder)
                .map(ok -> this);
    }

    public List<AuthorName> getNames() {
        return names.getNames();
    }

    public Either<AuthorError, Author> setBirthYear(@Nullable Integer birthYear) {
        return lifeAges.setBirthYear(birthYear)
                .map(ok -> this);
    }

    public Either<AuthorError, Author> setDeathYear(@Nullable Integer deathYear) {
        return lifeAges.setDeathYear(deathYear)
                .map(ok -> this);
    }

    public Either<AuthorError, Author> addLanguage(@Nonnull Language language) {
        return languages.add(language)
                .map(ok -> this);
    }

    public Either<AuthorError, Author> setDefaultLanguage(@Nonnull Long languageId) {
        return languages.setDefault(languageId)
                .map(ok -> this);
    }

    public Either<AuthorError, Author> replaceLanguage(@Nonnull Long languageId, @Nonnull Language newLanguage) {
        return languages.replace(languageId, newLanguage)
                .map(ok -> this);
    }

    public Either<AuthorError, Author> removeLanguage(@Nonnull Long languageId) {
        return languages.remove(languageId)
                .map(ok -> this);
    }

    public void setWhereabouts(@Nullable String newValue) {
        this.whereabouts = newValue;
    }

    public void updatePhoto(AuthorPhoto photo) {
        this.photo = photo.getBytes();
    }

    public void removePhoto() {
        this.photo = null;
    }
}
