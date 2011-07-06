package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criterion;

/**
 * Empty expression.
 *
 * @author Maciej Szczytowski <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public class EmptyExpression extends AbstractEmptinessExpression implements Criterion {

    /**
     * Create new empty expression.
     *
     * @param property property
     */
    protected EmptyExpression(String property) {
        super(property);
    }

    @Override
    protected boolean excludeEmpty() {
        return false;
    }
}
