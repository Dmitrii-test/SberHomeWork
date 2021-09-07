package ru.dmitrii.dao;

import ru.dmitrii.model.Person;
import java.sql.Connection;

public interface PersonDAO {
    Connection connection = null;

    void save(Person person);

    String show(int id);

    void update(int id, Person updatedPerson);

    void delete(int id);

}
