package lunchee.books.dictionary;

import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;
import static lunchee.books.utility.Validation.requireNotNull;

@NoArgsConstructor(access = PROTECTED)
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
