package io.github.srhojo.utils.commons.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * Specification search interface
 *
 * @param <T> Entity
 * @author srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 */
public interface ReadDao<T, ID extends Serializable> {

    /**
     * Method to retrieve an entity by its id
     *
     * @param id Entity's id
     * @return the entity
     */
    T get(ID id);

    /**
     * Searching method by specification (qwery languge).
     *
     * @param spec Specification to search
     * @return List of entities
     */
    List<T> search(Specification<T> spec);

    /**
     * Searching method by specification and pagination properties
     *
     * @param spec     Specification to search
     * @param pageable Pageable properties
     * @return Page of entities
     */
    Page<T> search(Specification<T> spec, Pageable pageable);

}
