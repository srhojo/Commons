package io.github.srhojo.utils.commons.ql.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author hojo
 *
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
     * Method to build a new Specification from list of search criteria instance.
     *
     * @return new GeneralSpecification with all search criteria
     */
    public CustomSpecification<T> build() {
        if (params.isEmpty()) {
            return null;
        }
        CustomSpecification<T> result = new CustomSpecification<>(params.get(0));
        for (int i = 1; i < params.size(); i++) {
            result = (CustomSpecification<T>) Specification.where(result).and(new CustomSpecification<>(params.get(i)));
        }
        return result;
    }

}
