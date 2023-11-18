package repositories;

import models.Subscribers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SubscribersCrudOperations implements CrudOperations<Subscribers> {

    private Subscribers parseSubscriber(ResultSet results) throws SQLException {
        Subscribers subscribers = new Subscribers();
        subscribers.setId(results.getLong("id"));
        subscribers.setName(results.getString("name"));
        subscribers.setReference(results.getString("reference"));
        return subscribers;
    }


    @Override
    public Subscribers save(Subscribers value) {
        return null;
    }

    @Override
    public List<Subscribers> saveAll(List<Subscribers> values) {
        return null;
    }

    @Override
    public List<Subscribers> findAll() {
        return null;
    }

    @Override
    public Subscribers delete(Subscribers value) {
        return null;
    }
}
