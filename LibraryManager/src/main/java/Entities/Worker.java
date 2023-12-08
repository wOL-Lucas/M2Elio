package Entities;


import lombok.Getter;


@Getter
public class Worker extends User{
    private Role role;

    public Worker(int id,String name, String birthDate, String username, String password, Role role) {
        super(id,name, birthDate, username, password);
        this.role = role;
    }


}
