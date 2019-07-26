package io.github.srhojo.utils.commons.ql.converter;

@FunctionalInterface
public interface StringConverter<U> {

    U map(final String value, final Class<U> clazz);
}
