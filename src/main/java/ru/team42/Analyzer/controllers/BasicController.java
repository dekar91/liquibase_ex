package ru.team42.Analyzer.controllers;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BasicController {
    @PersistenceContext
    EntityManager entityManager;
}
