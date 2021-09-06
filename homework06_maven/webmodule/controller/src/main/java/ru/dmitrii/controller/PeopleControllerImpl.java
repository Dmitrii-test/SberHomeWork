package ru.dmitrii.controller;

import ru.dmitrii.dao.PersonDAO;
import ru.dmitrii.viewers.WebApp;

public class PeopleControllerImpl implements PeopleController {
    PersonDAO personDAO;
    WebApp webApp;

    public void run () {
        webApp.view(personDAO.show(0));
    }

}
