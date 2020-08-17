package ru.team42.analyzer.controllers;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BasicController {
    @PersistenceContext
    protected EntityManager entityManager;
}
