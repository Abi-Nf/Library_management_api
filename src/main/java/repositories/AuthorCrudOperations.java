package repositories;

import configurations.DbConnect;
import models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorCrudOperations implements CrudOperations<Author> {
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

    @Override
    public Author save(Author value) {
        String sql = """
                insert into "author" (name, gender) values (?, ?);
                """;
        try {
            assert this.connection != null;
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, value.getName());
            statement.setString(2, String.valueOf(value.getGender()));

            boolean isInserted = statement.executeUpdate() == 1;
            if(isInserted) return this.find(value);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Author> saveAll(List<Author> values) {
        List<Author> authorList = new ArrayList<>();
        for (Author author : values) {
            Author saved = this.save(author);
            if(saved != null) authorList.add(saved);
        }
        return authorList;
    }

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        String sql = """
                select * from "author"
                """;

        try {
            ResultSet result = this.statementFinding(sql);
            while(result.next()) authors.add(
                this.parseAuthor(result)
            );
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return authors;
    }

    @Override
    public Author delete(Author value) {
        Author toDelete = this.find(value);
        String sql = """
                delete from "author" where name = ? and gender = ?
                """;

        try {
            assert this.connection != null;
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, value.getName());
            statement.setString(2, String.valueOf(value.getGender()));

            boolean isDeleted = statement.executeUpdate() == 1;
            if(isDeleted) return toDelete;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
