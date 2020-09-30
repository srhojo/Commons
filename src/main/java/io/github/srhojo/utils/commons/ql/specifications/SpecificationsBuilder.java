package io.github.srhojo.utils.commons.ql.specifications;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hojo
 */
public class SpecificationsBuilder<T> {

    private final List<SearchCriteria> params;

    public SpecificationsBuilder() {
        params = new ArrayList<>();
    }

    /**
     * final method to add new search criteria into specification builder instance
     *
     * @param key       object key
     * @param operation operation String
     * @param value     key value
     */
    public final void with(final String key, final String operation, final Object value) {
        SearchOperationEnum.getSimpleOperation(operation)
                .ifPresent(op -> params.add(new SearchCriteria(key, op, value)));
    }

    /**
     * final method to add new search criteria into specification builder instance
     *
     * @param orPredicate or key
     * @param key         object key
     * @param operation   operation String
     * @param value       key value
     */
    public final void with(final String orPredicate, final String key, final String operation, final String value) {
        SearchOperationEnum.getSimpleOperation(operation)
                .ifPresent(op -> params.add(new SearchCriteria(orPredicate, key, op, value)));
    }

    /**
     * Method to build a new Specification from list of search criteria instance.
     *
     * @return new GeneralSpecification with all search criteria
     */
    public Specification<T> build() {
        if (params.isEmpty()) {
            return null;
        }

        final List<Specification> specs = params.stream()
                .map(CustomSpecification::new)
                .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                    .isOrPredicate() ? Specification.where(result).or(specs.get(i))
                    : Specification.where(result).and(specs.get(i));
        }
        return result;
    }

}
