import Entities.Library;

import java.sql.SQLException;

public class LibraryManager {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Library library = new Library();
        library.mainloop();
    }
}