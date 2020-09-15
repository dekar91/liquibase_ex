package ru.team.example.services.interfaces;

import java.util.List;

public interface CrudServiceInterface<T> {

    List<T> getAll();
    T getById(Long id);
    T save(T dto);
    void delete(Long id);

}
