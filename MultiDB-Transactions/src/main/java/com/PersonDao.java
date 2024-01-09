package com;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDao {

    public static final RowMapper<Person> PERSON_ROW_MAPPER = (row, index) ->
            Person.builder()
                    .id(row.getLong("id"))
                    .name(row.getString("name"))
                    .createdAt(row.getDate("createdAt"))
                    .build();
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
        return get(source).query("Select * from person where name = ?", PERSON_ROW_MAPPER, name);
    }

    public List<Person> findAll(Source source) {
        return get(source).query("Select * from person", PERSON_ROW_MAPPER);
    }

    public Person add(Source source, Person person) {
         get(source).update("insert into person (name, createdAt) values (? , sysdate)", person.getName());
         return person;
    }

}
