package io.github.srhojo.utils.commons.dao;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * CRUD Base dao
 *
 * @author srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 *
 * @param <T>  Entity
 * @param <ID> Entity's id. Must be serializable
 */
public interface CreateDao<T, ID> {

    /**
     * Method to create a new entity
     *
     * @param t new entity
     * @return created entity
     */
    T create(T t);

    /**
     * Method to update a previous entity
     *
     * @param t Entity to update
     * @return updated entity
     */
    T update(T t);

    /**
     * Method to retrieve an entity by its id
     *
     * @param id Entity's id
     * @return the entity
     */
    T get(ID id);

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
    default List<T> create(final List<T> list) {
        return list.stream().map(this::create).collect(Collectors.toList());
    }

    /**
     * Method to update a list of entities
     *
     * @param list List of entities
     * @return List of updated entities
     */
    default List<T> update(final List<T> list) {
        return list.stream().map(this::update).collect(Collectors.toList());
    }
}
