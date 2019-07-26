package io.github.srhojo.utils.commons.mappers;

/**
 * <p>
 * Interface who extend from InnerMapper and OuterMapper interfaces
 * </p>
 *
 * @author: srhojo
 * @see <a href="https://github.com/srhojo">GitHub</a>
 *
 * @param <I> Inner Object
 * @param <O> Outer Object
 *
 */
public interface Mapper<I, O> extends InnerMapper<I, O>, OuterMapper<O, I> {
}
