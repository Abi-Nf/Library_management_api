package repositories;

import configurations.DbConnect;
import models.Subscribers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscribersCrudOperations implements CrudOperations<Subscribers> {
    private final Connection connection = DbConnect.connect();

    private Subscribers parseSubscriber(ResultSet results) throws SQLException {
        Subscribers subscribers = new Subscribers();
        subscribers.setId(results.getLong("id"));
        subscribers.setName(results.getString("name"));
        subscribers.setReference(results.getString("reference"));
        return subscribers;
    }

    private Subscribers find(Subscribers subscribers){
        String sql = """
                select id, name, reference from "subscribers"
                where name = ?
                """;

        try {
            ResultSet results = this.statementFinding(sql, subscribers);;
            if(results.next()) return this.parseSubscriber(results);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ResultSet statementFinding(String sql, Subscribers subscribers) throws SQLException {
        assert this.connection != null;
        PreparedStatement statement = this.connection.prepareStatement(sql);
        statement.setString(1, subscribers.getName());
        statement.setString(2, String.valueOf(subscribers.getReference()));
        return statement.executeQuery();
    }

    private ResultSet statementFinding(String sql) throws SQLException {
        assert this.connection != null;
        PreparedStatement statement = this.connection.prepareStatement(sql);
        return statement.executeQuery();
    }

    @Override
    public Subscribers save(Subscribers value) {
        String sql = """
                insert into "subscribers" (name) values (?)
                """;
        try {
            assert this.connection != null;
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, value.getName());

            boolean isInserted = statement.executeUpdate() == 1;
            if(isInserted) return this.find(value);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Subscribers> saveAll(List<Subscribers> values) {
        List<Subscribers> subscribers = new ArrayList<>();
        for (Subscribers value : values) subscribers.add(this.save(value));
        return subscribers;
    }

    @Override
    public List<Subscribers> findAll() {
        List<Subscribers> subscribers = new ArrayList<>();
        String sql = "select from \"subscribers\"";

        try {
            ResultSet result = this.statementFinding(sql);
            while(result.next()) subscribers.add(
                    this.parseSubscriber(result)
            );
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return subscribers;
    }

    @Override
    public Subscribers delete(Subscribers value) {
        Subscribers toDelete = this.find(value);
        String sql = """
                delete from "subscribers" where name = ?
                """;
        try {
            assert this.connection != null;
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, value.getName());

            boolean isDeleted = statement.executeUpdate() == 1;
            if(isDeleted) return toDelete;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
