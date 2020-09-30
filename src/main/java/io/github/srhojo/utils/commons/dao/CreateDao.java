package io.github.srhojo.utils.commons.dao;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CRUD Base dao
 *
 * @param <T>  Entity
 * @param <ID> Entity's id. Must be serializable
 * @author srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 */
public interface CreateDao<T, ID> {

    /**
     * Method to save an entity into BBDD
     *
     * @param t new entity
     * @return created entity
     */
    T save(T t);

    /**
     * Method to remove an entity from BBDD
     *
     * @param id Entity's id
     */
    void delete(ID id);

    /**
     * Method to create a list of entities
     *
     * @param list List of entities
     * @return List of created entities
     */
    default List<T> save(final List<T> list) {
        return list.stream().map(this::save).collect(Collectors.toList());
    }
}
