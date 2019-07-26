package io.github.srhojo.utils.commons.ql.specifications;

import java.io.Serializable;

public class SearchCriteria implements Serializable {

    private static final long serialVersionUID = -8046505327772649554L;
    private String key;
    private SearchOperationEnum operation;
    private transient Object value;
    private boolean orPredicate;

    public SearchCriteria() {
        // Do nothing
    }

    public SearchCriteria(final String key, final SearchOperationEnum operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(final String key) {
        this.key = key;
    }

    /**
     * @return the operation
     */
    public SearchOperationEnum getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(final SearchOperationEnum operation) {
        this.operation = operation;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final Object value) {
        this.value = value;
    }

    /**
     * @return the orPredicate
     */
    public boolean isOrPredicate() {
        return orPredicate;
    }

    /**
     * @param orPredicate the orPredicate to set
     */
    public void setOrPredicate(final boolean orPredicate) {
        this.orPredicate = orPredicate;
    }

}
