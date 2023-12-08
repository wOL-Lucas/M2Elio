package Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @AllArgsConstructor @NoArgsConstructor
public class Author {

    private String name;
    private String birthDate;
    private List<Book> books;

}
