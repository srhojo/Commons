package io.github.srhojo.utils.commons.dao;

import org.springframework.data.jpa.repository.JpaRepository;

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
public interface CreateDaoV2<T, ID> {


    JpaRepository<T,ID> getRepository();

    /**
     * Method to save an entity into BBDD
     *
     * @param t new entity
     * @return created entity
     */
    default T save(T t) {
        return getRepository().save(t);
    }

    /**
     * Method to remove an entity from BBDD
     *
     * @param id Entity's id
     */
    default void delete(ID id){
        getRepository().deleteById(id);
    }

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
