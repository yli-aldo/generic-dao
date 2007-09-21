package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Criterion;

/**
 * In expression.
 *
 * @author Maciej Szczytowsko <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public class InExpression implements Criterion {

    private final String property;
    private final Object[] values;

    /**
     * Create new in expression.
     *
     * @param property property
     * @param values values
     */
    protected InExpression(String property, Object[] values) {
        this.property = property;
        this.values = values;
    }

    @Override
    public String toSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
        String sql = criteriaQuery.getPropertyName(property, criteria) + " in (";

        for (Object v : values) {
            criteriaQuery.setParam(v);
            sql += "?,";
        }

        return sql.substring(0, sql.length() - 1) + ")";
    }
}
