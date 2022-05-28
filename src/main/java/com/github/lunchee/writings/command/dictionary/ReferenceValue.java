package com.github.lunchee.writings.command.dictionary;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.github.lunchee.writings.command.utility.Validation.requireNotNull;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@ToString
public class ReferenceValue {
    private String typeCode;
    private String valueCode;

    public ReferenceValue(@Nonnull String typeCode, @Nonnull String valueCode) {
        this.typeCode = requireNotNull(typeCode, "typeCode");
        this.valueCode = requireNotNull(valueCode, "valueCode");
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getValueCode() {
        return valueCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceValue that = (ReferenceValue) o;
        return typeCode.equalsIgnoreCase(that.typeCode) && valueCode.equalsIgnoreCase(that.valueCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeCode.toLowerCase(), valueCode.toLowerCase());
    }
}
