package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository("Dao")
public class PersonDaoImp implements  PersonDao{
    private  static List<Person> Db = new ArrayList<>();
    @Override
    public int insertPerson(UUID id, Person person) {
        Db.add(new Person(id,person.getName()));
//        System.out.println("Person " + (Db.get(Db.size() - 1)).getName());
        return 1;
    }

    @Override
    public List<Person> getAllPerson() {
        return Db;
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = selectPersonById(id);
        if (person.isEmpty()){
            return 0;
        }else {
            Db.remove(person.get());
            return 1;
        }
    }

    @Override
    public int updatePersonById(UUID id,Person person) {
        return selectPersonById(id).map(p -> {
            int indexOfPerson = Db.indexOf(p);
            if (indexOfPerson >= 0){
                Db.set(indexOfPerson,new Person(id,person.getName()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return Db.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }
}
