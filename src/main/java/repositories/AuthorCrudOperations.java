package repositories;

import configurations.DbConnect;
import models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorCrudOperations {
    private final Connection connection = DbConnect.connect();

    private ResultSet statementFinding(String sql, Author author) throws SQLException {
        assert this.connection != null;
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, author.getName());
        statement.setString(2, String.valueOf(author.getGender()));
        return statement.executeQuery();
    }

    private ResultSet statementFinding(String sql) throws SQLException {
        assert this.connection != null;
        PreparedStatement statement = this.connection.prepareStatement(sql);
        return statement.executeQuery();
    }

    private Author parseAuthor(ResultSet result) throws SQLException {
        Author author = new Author();
        author.setId(result.getLong("id"));
        author.setName(result.getString("name"));
        String gender = result.getString("gender");
        if(gender != null) author.setGender(gender.charAt(0));
        return author;
    }

    private Author find(Author author){
        String sql = """
                select id, name, gender from "author"
                where name = ? and gender = ?
                """;
        try {
            ResultSet results = this.statementFinding(sql, author);
            if(results.next()) return this.parseAuthor(results);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
