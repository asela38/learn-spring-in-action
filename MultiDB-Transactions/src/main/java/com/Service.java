package com;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@org.springframework.stereotype.Service
public class Service {
    private final PersonDao personDao;

    public Service(PersonDao personDao) {
        this.personDao = personDao;
    }

    public Person addPerson(PersonDao.Source source, String name) {
        return personDao.add(source, Person.builder().name(name).build());
    }


    public void add10People(PersonDao.Source source, int seed) {
        IntStream.iterate(seed, i -> ++i).limit(10).forEach( i -> {
                addPerson(source, "Subject " + i);
                if(i == 2005) {
                    throw new RuntimeException("Reach 2005");
                }
            }
        );
    }

    @Transactional(transactionManager = "secondTransactionManager", isolation = Isolation.DEFAULT)
    public void add10PeopleForAll(int seed1, int seed2) {
        add10People(PersonDao.Source.ONE, seed1);
        add10People(PersonDao.Source.TWO, seed2);
    }
}
