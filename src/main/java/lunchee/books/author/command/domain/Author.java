package lunchee.books.author.command.domain;

import io.vavr.control.Either;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lunchee.books.dictionary.Language;

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
    private final AuthorNames names = new AuthorNames(this);

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

    public Either<AuthorError, Author> changeName(@Nonnull Long id, @Nonnull AuthorName newName) {
        return names.change(id, newName)
                .map(ok -> this);
    }

    public Either<AuthorError, Author> removeName(@Nonnull Long id) {
        return names.remove(id)
                .map(ok -> this);
    }

    public List<AuthorNameEntity> getNames() {
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
