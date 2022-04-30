package lunchee.books.author.command;

import lunchee.books.author.command.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCommandRepository extends JpaRepository<Author, Long> {
}
