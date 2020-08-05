package io.github.srhojo.utils.commons.ql.expections;

public class QueryLanguageException extends RuntimeException {

    private static final long serialVersionUID = -4125253013397384481L;

    /**
     *
     * @param message The message of excepcition
     */
    public QueryLanguageException(final String message) {
        super(message);
    }

    /**
     *
     * @param message The message of exception
     * @param cause   The cause of exception
     */
    public QueryLanguageException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
