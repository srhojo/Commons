package io.github.srhojo.utils.commons.ql.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class OffsetPagination implements Pageable, Serializable {

    private static final long serialVersionUID = 3728222061064953889L;

    private final Integer limit;
    private final Long offset;
    private final Sort sort;

    /**
     * Private constructor. Use of to create new instances.
     *
     * @param limit  the size of the elements to be returned.
     * @param offset zero-based offset.
     * @param sort   can be {@literal null}.
     */
    private OffsetPagination(Integer limit, Long offset, Sort sort) {
        this.limit = limit;
        this.offset = offset;
        this.sort = sort != null ? sort : Sort.unsorted();
    }

    /**
     * Creates a new {@link OffsetPagination} with sort parameters applied.
     *
     * @param limit  the size of the elements to be returned.
     * @param offset zero-based offset.
     * @param sort   can be {@literal null}.
     * @return {@link OffsetPagination}
     */
    public static OffsetPagination of(
            @NotNull @Min(value = 1, message = "Limit size must not be less than one!") final Integer limit,
            @NotNull @Min(value = 0, message = "Offset index must not be less than zero!") final Long offset,
            final Sort sort) {
        return new OffsetPagination(limit, offset, sort);
    }

    /**
     * Creates a new {@link OffsetPagination} with sort parameter:
     * {@literal Sort.unsorted()}.
     *
     * @param limit  the size of the elements to be returned.
     * @param offset zero-based offset.
     * @return {@link OffsetPagination}
     */
    public static OffsetPagination of(
            @NotNull @Min(value = 1, message = "Limit size must not be less than one!") final Integer limit,
            @NotNull @Min(value = 0, message = "Offset index must not be less than zero!") final Long offset) {
        return of(limit, offset, Sort.unsorted());
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getLimit() {
        return limit;
    }

    @Override
    public int getPageNumber() {
        return offset.intValue() / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetPagination((int) getOffset() + getPageSize(), (long) getPageSize(), getSort());
    }

    private OffsetPagination previous() {
        return hasPrevious()
                ? new OffsetPagination((int) getOffset() + getPageSize(), (long) getPageSize(), getSort())
                : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetPagination(0, (long) getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

}
