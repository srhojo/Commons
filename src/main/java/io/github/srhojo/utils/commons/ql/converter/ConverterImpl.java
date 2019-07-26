package io.github.srhojo.utils.commons.ql.converter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import io.github.srhojo.utils.commons.ql.expections.QueryLanguageException;

public class ConverterImpl implements Converter {

    private final Map<Class<?>, StringConverter<?>> stringConverters;

    public ConverterImpl() {
        this.stringConverters = new HashMap<>();
        registerDefaultStringConverters();
    }

    @Override
    public <U> Object convert(final String value, final Class<? extends U> type) {
        final StringConverter<?> stringConverter = lookupStringConverter(type);
        return stringConverter.map(value, (Class) type);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void registerDefaultStringConverters() {
        stringConverters.put(String.class, (value, clazz) -> value);
        stringConverters.put(Integer.class, (value, clazz) -> Integer.valueOf(value));
        stringConverters.put(Boolean.class, (value, clazz) -> Boolean.valueOf(value));
        stringConverters.put(Enum.class, (value, clazz) -> Enum.valueOf((Class) clazz, value));
        stringConverters.put(Long.class, (value, clazz) -> Long.valueOf(value));
        stringConverters.put(Float.class, (value, clazz) -> Float.valueOf(value));
        stringConverters.put(Double.class, (value, clazz) -> Double.valueOf(value));
        stringConverters.put(BigDecimal.class, (value, clazz) -> new BigDecimal(value));
        stringConverters.put(LocalDate.class, (value, clazz) -> LocalDate.parse(value));
        stringConverters.put(LocalDateTime.class, (value, clazz) -> LocalDateTime.parse(value));
    }

    private StringConverter<?> lookupStringConverter(final Class<?> type) {
        final Optional<Entry<Class<?>, StringConverter<?>>> stringConverter = stringConverters.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(type)).findFirst();
        return stringConverter
                .orElseThrow(
                        () -> new QueryLanguageException("Not converter found for [" + type.getTypeName() + "] type"))
                .getValue();
    }
}
