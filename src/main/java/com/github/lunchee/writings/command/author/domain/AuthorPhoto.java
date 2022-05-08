package com.github.lunchee.writings.command.author.domain;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static com.github.lunchee.writings.command.utility.Validation.requireNotNull;

public class AuthorPhoto {

    private final byte[] bytes;

    public AuthorPhoto(@Nonnull byte[] bytes) {
        this.bytes = requireNotNull(bytes, "photoBytes");
    }

    public byte[] getBytes() {
        return Arrays.copyOf(bytes, bytes.length);
    }
}
