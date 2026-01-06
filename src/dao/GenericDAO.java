package dao;

import java.util.List;

public interface GenericDAO<T> {

    void insert(T obj) throws Exception;

    void update(T obj) throws Exception;

    void delete(T obj) throws Exception;

    T findById(Integer id) throws Exception;

    List<T> findAll() throws Exception;
}
