import models.Book;
import repositories.BookCrudOperations;

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

    public static void main(String[] args) {
        bookTest();
    }
}
