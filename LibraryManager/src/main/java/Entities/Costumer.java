package Entities;

import lombok.Getter;

import java.util.List;

@Getter
public class Costumer extends User{
    private List<Book> rentList;

    public Costumer(int id,String name, String birthDate, String username, String password, List<Book> rentList) {
        super(id,name, birthDate, username, password);
        this.rentList = rentList;
    }

}
