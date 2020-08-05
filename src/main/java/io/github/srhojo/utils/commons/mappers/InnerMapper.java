package io.github.srhojo.utils.commons.mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * InnerMapper: interface that define the methods for inner mapper
 * </p>
 *
 * @author srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 *
 * @param <I> Inner object who will be result of mapping
 * @param <O> Outer object to map.
 */
public interface InnerMapper<I, O> {

    /**
     * Map method from outer to inner object
     *
     * @param outer object
     * @return Inner object
     */
    I mapToInner(O outer);

    /**
     * Method to map outer to inner object. The result is an optional object who is
     * possible to contains null value
     *
     * @param outer object to try to map
     * @return Optional inner object
     */
    default Optional<I> toInner(final O outer) {
        return Optional.ofNullable(outer != null ? mapToInner(outer) : null);
    }

    /**
     * Wrapper method to map a list of outer objects.
     *
     * @param outer List of outer object
     * @return List of an inner objects
     */
    default List<I> toInner(final List<O> outer) {
        return outer != null ? outer.stream().map(this::mapToInner).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
