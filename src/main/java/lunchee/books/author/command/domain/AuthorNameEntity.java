package lunchee.books.author.command.domain;

import lombok.NoArgsConstructor;
import lunchee.books.dictionary.Language;

import javax.annotation.Nonnull;
import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static lunchee.books.utility.Validation.requireNotNull;

@Entity(name = "AuthorName")
@NoArgsConstructor(access = PROTECTED)
public class AuthorNameEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Author author;

    @Embedded
    private AuthorName name;

    protected AuthorNameEntity(@Nonnull Author author, @Nonnull AuthorName name) {
        this.author = requireNotNull(author, "author");
        this.name = requireNotNull(name, "name");
    }

    public Long getId() {
        return id;
    }

    public AuthorName getName() {
        return name;
    }

    public NameType getNameType() {
        return name.getType();
    }

    public Language getLanguage() {
        return name.getLanguage();
    }

    protected void removeAuthor() {
        this.author = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorNameEntity that = (AuthorNameEntity) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}