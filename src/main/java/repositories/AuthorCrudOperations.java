package repositories;

import models.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorCrudOperations implements CrudOperations<Author> {
    @Override
    public Author save(Author value) {
        String sql = """
                insert into "author" () values ();
                select * from "author" where ;
                """;
        return null;
    }

    @Override
    public List<Author> saveAll(List<Author> values) {
        List<Author> authors = new ArrayList<>();
        for (Author value : values) {
            Author returned = this.save(value);
            authors.add(returned);
        }
        return authors;
    }

    @Override
    public List<Author> findAll() {
        String sql = """
                select * from "author";
                """;
        return null;
    }

    @Override
    public Author delete(Author value) {
        String sql = """
                delete from "author" where ;
                """;
        return null;
    }
}
