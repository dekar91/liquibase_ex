package ru.team.example.controllers;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Basic controller
 */
public abstract class BasicController {
    @PersistenceContext
    protected EntityManager entityManager;
}
