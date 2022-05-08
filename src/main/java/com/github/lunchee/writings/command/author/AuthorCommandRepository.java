package com.github.lunchee.writings.command.author;

import com.github.lunchee.writings.command.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCommandRepository extends JpaRepository<Author, Long> {
}
