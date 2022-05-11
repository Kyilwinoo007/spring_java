package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonProgressImp implements PersonDao{
    private  static List<Person> Db = new ArrayList<>();

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonProgressImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        String sql = "INSERT INTO person(id,name) VALUES(?,?)";
        return jdbcTemplate.update(sql,id,person.getName());
    }

    @Override
    public List<Person> getAllPerson() {
        String sql = "SELECT id,name FROM person";
        List<Person> lst = jdbcTemplate.query(sql,(rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            String name = rs.getString("name");
            return new Person(id,name);
        });
        return lst;
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = selectPersonById(id);
        if (person.isEmpty()){
            return 0;
        }else {
            String sql = "DELETE FROM person WHERE id = ?";
            return jdbcTemplate.update(sql,id);
        }
    }

    @Override
    public int updatePersonById(UUID id,Person person) {
        String sql = "UPDATE person SET name = ? WHERE id = ?";
        return  jdbcTemplate.update(sql,person.getName(),id);
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        String sql = "SELECT id,name FROM person WHERE id = ?";
        Person person = jdbcTemplate.queryForObject(sql,new Object[]{id},(rs, rowNum) -> {
            UUID personId = UUID.fromString(rs.getString("id"));
            String name = rs.getString("name");
            return new Person(personId,name);
        });
        return Optional.ofNullable(person);
    }
}
