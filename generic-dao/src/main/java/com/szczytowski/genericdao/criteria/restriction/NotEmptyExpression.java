package com.szczytowski.genericdao.criteria.restriction;

/**
 * Not empty expression.
 *
 * @author Maciej Szczytowski <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
import com.szczytowski.genericdao.criteria.Criterion;

public class NotEmptyExpression extends AbstractEmptinessExpression implements Criterion {

    /**
     * Create new not empty expression.
     *
     * @param property property
     */
    protected NotEmptyExpression(String property) {
        super(property);
    }

    @Override
    protected boolean excludeEmpty() {
        return true;
    }
}
