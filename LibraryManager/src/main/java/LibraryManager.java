import Entities.Library;

import java.sql.SQLException;

/*
    Olá professor. Seu login como costumer é:
    user: professor
    password: 123456

    Conforme conversamos por whatsapp, talvez a database acabe mudando de host, se puder por gentileza nos avisar em um dos contatos abaixo caso não consiga acessar a database:
    contato: lucasefernandes333@gmail.com
    whatsapp: (47) 9 9169-8634

    Vamos ficar de olho, e manter a instância on desde já.
    Muito obrigado!!!!!!

    Alunos: Lucas Eduardo F. Fernandes, Ricardo Eichinger, Ricardo Kauan e Jean Fabio Gruber

    github repo: https://github.com/wOL-Lucas/M2Elio

*/

public class LibraryManager {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Library library = new Library();
        library.mainloop();
    }
}