package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Criterion;

/**
 * Not null expression.
 *
 * @author Maciej Szczytowsko <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public class NotNullExpression implements Criterion {

    private final String property;

    /**
     * Create new not null expression.
     *
     * @param property property
     */
    protected NotNullExpression(String property) {
        this.property = property;
    }

    @Override
    public String toSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
        return criteriaQuery.getPropertyName(property, criteria) + " is not null";
    }
}
