package io.github.srhojo.utils.commons.ql.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import io.github.srhojo.utils.commons.ql.converter.Converter;
import io.github.srhojo.utils.commons.ql.converter.ConverterImpl;

/**
 * @author hojo
 */
public class CustomSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = -594590569552959173L;

    private final SearchCriteria criteria;
    private final Converter converter;

    public CustomSpecification(final SearchCriteria criteria) {
        this.criteria = criteria;
        this.converter = new ConverterImpl();
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query,
            final CriteriaBuilder criteriaBuilder) {
        switch (criteria.getOperation()) {
        case EQUALITY:
            return criteriaBuilder.equal(root.get(criteria.getKey()),
                    converter.convert(criteria.getValue().toString(), getKeyClass(root, criteria.getKey())));
        case NEGATION:
            return criteriaBuilder.notEqual(root.get(criteria.getKey()),
                    converter.convert(criteria.getValue().toString(), getKeyClass(root, criteria.getKey())));
        case GREATER_THAN:
            return criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
        case LESS_THAN:
            return criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
        case LIKE:
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(criteria.getKey())), "%"
                    + converter.convert(criteria.getValue().toString().toUpperCase(), getKeyClass(root, criteria.getKey())) + "%");
        default:
            return null;
        }
    }

    private Class<?> getKeyClass(final Root<T> root, final String key) {
        return root.getModel().getAttribute(key).getJavaType();
    }

}
