package ru.team42.analyzer.controllers;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BasicController {
    @PersistenceContext
    protected EntityManager entityManager;
}
