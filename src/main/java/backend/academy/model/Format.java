package backend.academy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Format {
    MD("markdown"),
    ADOC("adoc");

    private final String value;

    public static Format getFormat(String value) throws IllegalArgumentException {
        for (Format format : values()) {
            if (format.value.equalsIgnoreCase(value)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Invalid format value: " + value);
    }
}
