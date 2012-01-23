package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Criterion;

/**
 * Abstract emptiness expression.
 *
 * @author Maciej Szczytowski <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public abstract class AbstractEmptinessExpression implements Criterion {

    protected final String property;

    /**
     * Create new emptiness expression.
     *
     * @param property property
     */
    protected AbstractEmptinessExpression(String property) {
        this.property = property;
    }

    /**
     * Check if exclude empty.
     *
     * @return true if exlude empry
     */
    protected abstract boolean excludeEmpty();

    @Override
    public final String toSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
        return (excludeEmpty() ? "exists" : "not exists") + " (select 1 from " + criteriaQuery.getPropertyName(property, criteria) + ")";
    }
}
