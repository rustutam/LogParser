package backend.academy.model;

import lombok.Getter;

@Getter
public enum Format {
    MD("markdown"),
    ADOC("adoc");

    private final String value;

    Format(String value) {
        this.value = value;
    }

    public static Format getFormat(String value) throws IllegalArgumentException {
        for (Format format : values()) {
            if (format.value.equalsIgnoreCase(value)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Invalid format value: " + value);
    }
}
