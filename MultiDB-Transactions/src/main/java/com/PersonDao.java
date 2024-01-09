package com;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDao {

    private final JdbcTemplate firstJdbcTemplate;
    private final JdbcTemplate secondJdbcTemplate;

    public PersonDao(JdbcTemplate firstJdbcTemplate, JdbcTemplate secondJdbcTemplate) {
        this.firstJdbcTemplate = firstJdbcTemplate;
        this.secondJdbcTemplate = secondJdbcTemplate;
    }

    public enum Source { ONE, TWO}
    private JdbcTemplate get(Source source) {
        return switch(source) {
            case ONE -> firstJdbcTemplate;
            case TWO -> secondJdbcTemplate;
            default -> null;
        };
    }

    public List<Person> find(Source source, String name) {
        return get(source).query("Select * from person where name = ?",(row, index) ->
                Person.builder()
                        .name(row.getString("name"))
                        .createdAt(row.getDate("createdAt"))
                        .build(), name);
    }

}
