package io.github.srhojo.utils.commons.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Specification search interface
 *
 * @param <T> Entity
 * @author srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 */
public interface ReadDaoV2<T, ID extends Serializable> {


    JpaRepository<T, ID> getRepository();

    /**
     * Method to retrieve an entity by its id
     *
     * @param id Entity's id
     * @return the entity
     */
    default T get(final ID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Entity with id '%s' not found", id)));
    }

    /**
     * Method to retrieve all entities from table.
     *
     * @return List of entities 'T'
     */
    default List<T> getAll() {
        return getRepository().findAll();
    }

    /**
     * TODO: To complete
     * Searching method by specification (qwery languge).
     *
     * @param spec Specification to search
     * @return List of entities
     */
    //List<T> search(Specification<T> spec);

    /**
     * TODO: To complete
     * Searching method by specification and pagination properties
     *
     * @param spec     Specification to search
     * @param pageable Pageable properties
     * @return Page of entities
     */
    //Page<T> search(Specification<T> spec, Pageable pageable);

}
