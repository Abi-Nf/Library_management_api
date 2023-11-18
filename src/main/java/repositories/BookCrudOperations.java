package repositories;

import configurations.DbConnect;
import models.Book;

import java.sql.Connection;
import java.util.List;

public class BookCrudOperations implements CrudOperations<Book> {
    private Connection connection = DbConnect.connect();

    @Override
    public Book save(Book value) {
        return null;
    }

    @Override
    public List<Book> saveAll(List<Book> values) {
        return null;
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Book delete(Book value) {
        return null;
    }
}
