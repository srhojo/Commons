package io.github.srhojo.utils.commons.ql.domain;

import java.util.List;

public class ContainerList<T> {
    private List<T> values;
    private OffsetPagination pagination;

    public static ContainerList of(List values, OffsetPagination pagination) {
        ContainerList container = new ContainerList();
        container.setValues(values);
        container.setPagination(pagination);
        return container;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public OffsetPagination getPagination() {
        return pagination;
    }

    public void setPagination(OffsetPagination pagination) {
        this.pagination = pagination;
    }
}
