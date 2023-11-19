import models.Author;
import models.Book;
import models.Subscribers;
import repositories.AuthorCrudOperations;
import repositories.BookCrudOperations;
import repositories.SubscribersCrudOperations;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void bookTest(){
        BookCrudOperations operations = new BookCrudOperations();

        Book createBook = new Book();
        createBook.setBookName("Nightmare halley");
        createBook.setTopic("OTHER");
        createBook.setPageNumbers(250);
        createBook.setReleaseDate(LocalDate.of(2020, 1, 1));
        System.out.println(
            operations.save(createBook)
        );

        List<Book> books = operations.findAll();
        System.out.println(books);

        if(books.size() == 0) return;
        Book book = books.get(0);

        Book deleted = operations.delete(book);
        if(deleted != null) System.out.printf(
            "id: %s; name: %s; topic: %s; page number: %s; release date: %s; author: %s",
            deleted.getId(),
            deleted.getBookName(),
            deleted.getTopic(),
            deleted.getPageNumbers(),
            deleted.getReleaseDate(),
            deleted.getAuthor()
        );
    }

    public static void authorTest(){
        AuthorCrudOperations operations = new AuthorCrudOperations();
        System.out.println(operations.findAll());


        Author createAuthor = new Author();
        createAuthor.setName("Mickael");
        createAuthor.setGender('M');

        Author savedAuthor = operations.save(createAuthor);
        System.out.println(savedAuthor.getId());

        Author deleted = operations.delete(savedAuthor);
        System.out.println(deleted.getName());
    }

    public static void subscribersTest(){
        SubscribersCrudOperations operations = new SubscribersCrudOperations();

        Subscribers subscriberTest = new Subscribers();
        subscriberTest.setName("I am testing");

        Subscribers saved = operations.save(subscriberTest);
        System.out.println(saved.getId());
        System.out.println(saved.getName());
        System.out.println(saved.getReference());
    }

    public static void main(String[] args) {
        bookTest();
        authorTest();
        subscribersTest();
    }
}
