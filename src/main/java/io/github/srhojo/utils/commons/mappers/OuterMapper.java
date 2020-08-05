package io.github.srhojo.utils.commons.mappers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * OuterMapper: interface that define the methods for outer mapper
 * </p>
 * 
 * @author srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 *
 * @param <O> Outer object who will be result of mapping
 * @param <I> Inner object to map
 */
public interface OuterMapper<O, I> {
    /**
     * Map method from inner to outer object
     *
     * @param inner object to map
     * @return outer object
     */
    O mapToOuter(I inner);

    /**
     * @param inner object to try to map
     * @return Optional outer object
     */
    default Optional<O> toOuter(final I inner) {
        return Optional.ofNullable(inner != null ? mapToOuter(inner) : null);
    }

    /**
     * @param inner List of inner
     * @return List of an outer objects
     */
    default List<O> toOuter(final List<I> inner) {
        return inner != null ? inner.stream().map(this::mapToOuter).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
