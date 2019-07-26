package io.github.srhojo.utils.commons.ql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import io.github.srhojo.utils.commons.ql.specifications.SearchOperationEnum;
import io.github.srhojo.utils.commons.ql.specifications.SpecificationsBuilder;

/**
 * @author hojo
 *
 */
@Component
public class QueryLanguajeComponentImpl<T> implements QueryLanguajeComponent<T> {

    private static final String PATTERN_FORMAT = "(\\w+?)(%s)(\\w+?),";

    @Override
    public Specification<T> parse(final String filter) {

        final SpecificationsBuilder<T> builder = new SpecificationsBuilder<>();

        final String operationSetExper = String.join("|", SearchOperationEnum.operationSet());

        final Pattern pattern = Pattern.compile(String.format(PATTERN_FORMAT, operationSetExper),
                Pattern.UNICODE_CHARACTER_CLASS);

        final Matcher matcher = pattern.matcher(filter + ",");

        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        return builder.build();
    }

}
