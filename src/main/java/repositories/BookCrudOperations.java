package repositories;

import configurations.DbConnect;
import models.Author;
import models.Book;

import java.sql.*;

public class BookCrudOperations {
    private final Connection connection = DbConnect.connect();

    private ResultSet statementFinding(String sql, Book book) throws SQLException {
        assert this.connection != null;
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, book.getBookName());
        statement.setString(2, book.getTopic());
        statement.setDate(3, Date.valueOf(book.getReleaseDate()));
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

    // redundancies
}
