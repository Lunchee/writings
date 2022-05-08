package com.github.lunchee.writings.command.author.domain;

import lombok.NoArgsConstructor;
import com.github.lunchee.writings.command.dictionary.Language;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static com.github.lunchee.writings.command.utility.Validation.requireNotNull;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class AuthorLanguage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Author author;

    private Language language;

    private boolean isDefault;

    protected AuthorLanguage(@Nonnull Author author, @Nonnull Language language, boolean isDefault) {
        this.author = author;
        this.language = requireNotNull(language, "language");
        this.isDefault = isDefault;
    }

    public Long getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(@Nonnull Language language) {
        this.language = language;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void resetDefault() {
        this.isDefault = false;
    }

    public void setAsDefault() {
        this.isDefault = true;
    }

    protected void removeAuthor() {
        this.author = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorLanguage that = (AuthorLanguage) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
