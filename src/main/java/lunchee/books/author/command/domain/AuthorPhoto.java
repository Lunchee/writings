package lunchee.books.author.command.domain;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static lunchee.books.utility.Validation.requireNotNull;

public class AuthorPhoto {

    private final byte[] bytes;

    public AuthorPhoto(@Nonnull byte[] bytes) {
        this.bytes = requireNotNull(bytes, "photoBytes");
    }

    public byte[] getBytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }
}
