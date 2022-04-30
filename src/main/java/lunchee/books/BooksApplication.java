package lunchee.books;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BooksApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BooksApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
