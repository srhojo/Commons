package io.github.srhojo.utils.commons.ql;

import org.springframework.data.jpa.domain.Specification;

public interface QueryLanguajeComponent<T> {

    Specification<T> parse(final String filter);

}
