package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Criterion;

/**
 * ILike expression.
 *
 * @author Maciej Szczytowsko <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public class IlikeExpression implements Criterion {

    private final String property;
    private final Object value;

    /**
     * Create new ilike expression.
     *
     * @param property property
     * @param value value
     */
    protected IlikeExpression(String property, Object value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public String toSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
        criteriaQuery.setParam(value);
        return criteriaQuery.getPropertyName(property, criteria) + " ilike ?";
    }
}
