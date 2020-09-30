package io.github.srhojo.utils.commons.ql.specifications;

import java.util.Optional;
import java.util.stream.Stream;

public enum SearchOperationEnum {
    EQUALITY("=="),
    NEGATION("!="),
    GREATER_THAN("=gt="),
    LESS_THAN("=lt="),
    LIKE("=in="),
    OR("'");

    private final String symbol;

    SearchOperationEnum(final String symbol) {
        this.symbol = symbol;
    }

    public static String[] operationSet() {
        return Stream.of(SearchOperationEnum.values()).map(SearchOperationEnum::getSymbol).toArray(String[]::new);
    }

    public static Optional<SearchOperationEnum> getSimpleOperation(final String input) {
        SearchOperationEnum operation;
        switch (input) {
            case "==":
                operation = EQUALITY;
                break;
            case "!=":
                operation = NEGATION;
                break;
            case "=gt=":
                operation = GREATER_THAN;
                break;
            case "=lt=":
                operation = LESS_THAN;
                break;
            case "=in=":
                operation = LIKE;
                break;
            case "'":
                operation = OR;
                break;
            default:
                operation = null;
                break;
        }
        return Optional.ofNullable(operation);
    }

    /**
     * @return the symbol of operation
     */
    public String getSymbol() {
        return symbol;
    }
}
