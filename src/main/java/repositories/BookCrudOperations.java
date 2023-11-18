package repositories;

import configurations.DbConnect;
import models.Author;
import models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookCrudOperations implements CrudOperations<Book> {
    private final Connection connection = DbConnect.connect();

    private ResultSet statementFinding(String sql, Book book) throws SQLException {
        assert this.connection != null;
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, book.getBookName());
        statement.setString(2, book.getTopic());

        if(book.getReleaseDate() != null){
            Date date = Date.valueOf(book.getReleaseDate());
            statement.setDate(3, date);
        }else {
            statement.setNull(3, Types.DATE);
        }

        statement.setInt(4, book.getPageNumbers());
        return statement.executeQuery();
    }

    private ResultSet statementFinding(String sql) throws SQLException {
        assert this.connection != null;
        PreparedStatement statement = this.connection.prepareStatement(sql);
        return statement.executeQuery();
    }

    private Book find(Book book){
        String sql = """
                select book.id as book_id, a.id as author_id, * from "book"
                full outer join "author" a on a.id = book.author
                where bookname = ? and topic = ?::topic and releasedate = ? and pagenumbers = ?
                """;
        try {
            ResultSet result = this.statementFinding(sql, book);
            if(result.next()) return parseBook(result);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    private Book parseBook(ResultSet result) throws SQLException {
        Author author = new Author();
        author.setId(result.getLong("author_id"));
        author.setName(result.getString("name"));
        String varcharGender = result.getString("gender");
        if(varcharGender != null) author.setGender(varcharGender.charAt(0));

        Book newBook = new Book();
        newBook.setId(result.getLong("book_id"));
        newBook.setBookName(result.getString("bookname"));
        newBook.setTopic(result.getString("topic"));

        Date date = result.getDate("releasedate");
        if(date != null) newBook.setReleaseDate(date.toLocalDate());

        newBook.setPageNumbers(result.getInt("pagenumbers"));
        newBook.setAuthor(author);
        return newBook;
    }


    @Override
    public Book save(Book value) {
        String sql = String.format(
                """
                insert into "book" (bookname, pagenumbers, topic, releasedate)
                values (?, ?, '%s'::topic, ?);
                """,
                value.getTopic()
        );
        try {
            assert this.connection != null;
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, value.getBookName());
            statement.setInt(2, value.getPageNumbers());
            statement.setDate(3, Date.valueOf(value.getReleaseDate()));

            boolean isInserted = statement.executeUpdate() == 1;
            if(isInserted) return this.find(value);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Book> saveAll(List<Book> values) {
        List<Book> books = new ArrayList<>();
        for (Book book : values) {
            Book saved = this.save(book);
            if(saved != null) books.add(saved);
        }
        return books;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String sql = """
                select book.id as book_id, a.id as author_id, * from "book"
                full outer join author a on a.id = book.author
                """;
        try {
            ResultSet result = this.statementFinding(sql);
            while(result.next()) bookList.add(
                    this.parseBook(result)
            );
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return bookList;
    }

    @Override
    public Book delete(Book value) {
        Book toBeDeleted = this.find(value);
        String sql = String.format(
                """
                delete from "book" where bookname = ? and pagenumbers = ? and topic = '%s'::topic and releasedate = ?
                """,
                value.getTopic()
        );

        try {
            assert this.connection != null;
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, value.getBookName());
            statement.setInt(2, value.getPageNumbers());

            if(value.getReleaseDate() != null){
                Date date = Date.valueOf(value.getReleaseDate());
                statement.setDate(3, date);
            }else {
                statement.setNull(3, Types.DATE);
            }

            boolean isDeleted = statement.executeUpdate() == 1;
            if(isDeleted) return toBeDeleted;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
