package lunchee.books.author.query;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.Optional;

public interface AuthorQueryRepository {

    @SqlQuery("select * from author where id = :id")
    @RegisterConstructorMapper(Author.class)
    Optional<Author> findAuthorById(long id);

    @SqlQuery("select * from author_name where author_id = :id")
    @RegisterConstructorMapper(AuthorName.class)
    List<AuthorName> findAuthorNames(@BindBean Author author);

    @SqlQuery("select * from author_language where author_id = :id")
    @RegisterConstructorMapper(AuthorLanguage.class)
    List<AuthorLanguage> findAuthorLanguages(@BindBean Author author);
}
