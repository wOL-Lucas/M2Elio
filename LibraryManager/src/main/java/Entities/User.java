package Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor @AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String birthDate;
    private String username;
    private String password;
}
