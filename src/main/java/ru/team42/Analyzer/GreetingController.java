package ru.team42.Analyzer;


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.SessionFactoryBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class GreetingController {

    @PersistenceContext
    EntityManager entityManager;


    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    @Transactional
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting g = new Greeting();

        g.setContent(name);

        entityManager.persist(g);

        return  g;
    }
}