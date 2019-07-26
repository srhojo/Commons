package io.github.srhojo.utils.commons.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification search interface
 *
 * @author: srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 *
 * @param <T> Entity
 */
public interface SearchDao<T> {

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
