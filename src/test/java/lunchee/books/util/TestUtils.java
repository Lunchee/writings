package lunchee.books.util;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Path;

public class TestUtils {

    @SneakyThrows
    public static byte[] readResource(Path path) {
        try (InputStream resourceStream = TestUtils.class.getClassLoader().getResourceAsStream(path.toString())) {
            if (resourceStream == null) {
                throw new IllegalArgumentException("Resource is not found at path " + path);
            }

            return resourceStream.readAllBytes();
        }
    }
}
