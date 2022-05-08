package com.github.lunchee.writings.command.dictionary;

import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
public class Language extends ReferenceValue {

    public Language(@Nonnull String valueCode) {
        super("LANGUAGE", valueCode);
    }
}
