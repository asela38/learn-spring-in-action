package com;

@org.springframework.stereotype.Service
public class Service {
    private final PersonDao personDao;

    public Service(PersonDao personDao) {
        this.personDao = personDao;
    }
}
