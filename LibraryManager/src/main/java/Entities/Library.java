package Entities;

import Database.connector;
import lombok.Getter;
import Utilities.InputGetter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Library {
    private Connection connection = new connector().createConnection();
    private Stock stock = new Stock(this.getBooksFromDb());
    private List<Costumer> costumers = this.getCostumersFromDb();
    private List<Worker> workers = this.getWorkersFromDb();

    public List<Book> getBooksFromDb() throws SQLException {

        List<Book> books = new ArrayList<Book>();

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Book");

        while(result.next()){
            Statement queryAuthor = connection.createStatement();
            ResultSet resultAuthor = queryAuthor.executeQuery("SELECT * FROM Author WHERE id = " + result.getInt("id"));

            while(resultAuthor.next()){
                Author author = new Author(resultAuthor.getString("name"), resultAuthor.getString("birthDate"), new ArrayList<Book>());

                books.add(new Book(
                        result.getInt("id"),
                        result.getString("name"),
                        author,
                        Category.values()[result.getInt("category")],
                        result.getString("description"),
                        result.getBoolean("isRented")
                ));
            }



        }
        return books;
    }

    public List<Worker> getWorkersFromDb() throws SQLException {

        List<Worker> workers = new ArrayList<Worker>();

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Worker");

        while(result.next()){
            workers.add(new Worker(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("birthDate"),
                    result.getString("username"),
                    result.getString("password"),
                    Role.values()[result.getInt("role")]
            ));

        }
        return workers;
    }


    public List<Author> getAuthorsFromDb() throws SQLException {

        List<Author> authors = new ArrayList<Author>();

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Author");

        while(result.next()){
            authors.add(new Author(
                    result.getString("name"),
                    result.getString("birthDate"),
                    new ArrayList<Book>()
            ));

        }
        return authors;
    }



    public List<Costumer> getCostumersFromDb() throws SQLException {

        List<Costumer> costumers = new ArrayList<Costumer>();

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Costumer");

        while(result.next()){


            List<Book> rentList = new ArrayList<Book>();
            Statement queryRent = connection.createStatement();
            ResultSet resultRent = queryRent.executeQuery("SELECT book_id FROM Rents WHERE costumer_id = " + result.getInt("id") + " AND return_date IS NULL");

            while(resultRent.next()){
                for(Book book : stock.getBooks()){
                    if(book.getId() == resultRent.getInt("book_id")){
                        rentList.add(book);
                    }
                }
            }

            costumers.add(new Costumer(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("birthDate"),
                    result.getString("username"),
                    result.getString("password"),
                    rentList
            ));
        }
        return costumers;
    }


    public boolean rentBook(Costumer costumer, Book book) throws SQLException {
        if(!book.isRented()){

            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO Rents (costumer_id, book_id) VALUES (?, ?)");
            statement.setInt(1, costumer.getId());
            statement.setInt(2, book.getId());

            int result = statement.executeUpdate();

            if(result > 0){
                costumer.getRentList().add(book);
                stock.getBooks().remove(book);

                return true;
            }

            return false;
        }

        return false;
    }

    public boolean returnBook(Costumer costumer, Book book) throws SQLException {

        if(book.isRented()){
            PreparedStatement statement = this.connection.prepareStatement("UPDATE Rents SET return_date = ? WHERE costumer_id = ? AND book_id = ?");
            statement.setDate(1, new Date(System.currentTimeMillis()));
            statement.setInt(2, costumer.getId());
            statement.setInt(3, book.getId());


            int result = statement.executeUpdate();

            if(result > 0){
                costumer.getRentList().remove(book);
                stock.getBooks().add(book);

                return true;
            }

            return false;
        }
        return false;
    }

    public Costumer renewCostumerSessionInfo(Costumer costumer) throws SQLException {
        Costumer renewedCostumer = null;
        this.costumers = this.getCostumersFromDb();
        this.stock = new Stock(this.getBooksFromDb());

        for(Costumer c : costumers){
            if(costumer.getUsername().equals(c.getUsername()) && costumer.getPassword().equals(c.getPassword())){
                renewedCostumer = c;
            }
        }

        return renewedCostumer;
    }

    public Book getBookById(List<Book> books, int id){
        Book book = null;

        while(book == null){
            for(Book b : books){
                if(b.getId() == id){
                    book = b;
                    break;
                }
            }
            if(book != null){
                return book;
            }

            System.out.println("Invalid input, please try again\n");
            id = InputGetter.getInt("Enter the correct id of the book");
        }

        return book;

    }

    public Library() throws SQLException, ClassNotFoundException {
    }

    public void mainloop() throws SQLException {
        int action;
        boolean login = false;
        Costumer user = null;
        Worker employee;

        while(true){
            while(!login){
                int typeOfLogin = InputGetter.getInt("Welcome to the library! Please choose your type of login:\n1. Costumer\n2. Worker\n3. Exit");

                if(typeOfLogin == 1) {
                    String username = InputGetter.getString("Please enter your username");
                    String password = InputGetter.getString("Please enter your password");


                    for(Costumer costumer : costumers){
                        if(costumer.getUsername().equals(username) && costumer.getPassword().equals(password)){
                            login = true;
                            user = costumer;
                            break;
                        }
                        else{
                            System.out.println("Invalid username or password, try again");
                        }
                    }
                }

                else if(typeOfLogin == 2){
                    String username = InputGetter.getString("Please enter your username");
                    String password = InputGetter.getString("Please enter your password");

                    for(Worker worker : workers){
                        if(worker.getUsername().equals(username) && worker.getPassword().equals(password)){
                            login = true;
                            break;
                        }
                        else{
                            System.out.println("Invalid username or password, try again\n");
                        }
                    }
                }

                else if(typeOfLogin == 3){
                    System.out.println("Thank you for using our library!\n");
                    System.exit(0);
                }

                else{
                    System.out.println("Invalid input, please try again\n");
                }
            }

            action = InputGetter.getInt("What do you want to do?\n\n1 - for book rent\n2 - for book return\n3 - for logout\n4 - for exit\n");

            if(action == 1){
                System.out.println("Here is the list of books available for rent:\n");
                for(Book book : stock.getBooks()){
                    if(!book.isRented()){
                        System.out.println(book.toString());
                    }
                }

                int bookId = InputGetter.getInt("Enter the id of the book you want to rent");
                Book chosenBook = getBookById(stock.getBooks(), bookId);

                if(rentBook(user, chosenBook)){
                    user = renewCostumerSessionInfo(user);
                    System.out.println("You have successfully rented the book " + chosenBook.getName());
                }
                else{
                    System.out.println("Something went wrong, please try again");
                }

            }else if(action == 2){

                user = renewCostumerSessionInfo(user);
                List<Book> rentedBooks = user.getRentList();
                if(rentedBooks.size() > 0){
                    System.out.println("Here is the list of books you have rented:\n");
                    for(Book book : rentedBooks){
                        System.out.println(book.toString());
                    }
                    int bookId = InputGetter.getInt("Enter the id of the book you want to return");

                    Book chosenBook = getBookById(user.getRentList(), bookId);
                    user.getRentList().remove(chosenBook);

                    if(returnBook(user, chosenBook)){
                        user = renewCostumerSessionInfo(user);
                        System.out.println("You have successfully returned the book " + chosenBook.getName());
                    }
                    else{
                        System.out.println("Something went wrong, please try again");
                    }
                }
                else{
                    System.out.println("You have no rented books");
                }
            }
            else if(action == 3){
                login = false;
            }
            else if(action == 4){
                System.out.println("Thank you for using our library!\n");
                System.exit(0);
            }
            else{
                System.out.println("Invalid input, please try again\n");
            }

        }
    }
}
