package ru.team42.analyzer.services.interfaces;

import java.util.List;

public interface CrudServiceInterface<T> {

    List<T> getAll(Long id);
    T getById(Long id);
    T save(T dto);
    void delete(Long id);

}
