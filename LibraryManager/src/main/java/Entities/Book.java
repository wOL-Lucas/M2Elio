package Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class Book {

    private int id;
    private String name;
    private Author author;
    private Category category;
    private String description;
    private boolean isRented;

    @Override
    public String toString(){
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"author\":\"" + author.getName() + "\"," +
                "\"category\":\"" + category.toString() + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"isRented\":\"" + isRented + "\"" +
                "}";
    };

}
