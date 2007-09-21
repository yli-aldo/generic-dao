package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Criterion;

/**
 * Logical expression.
 *
 * @author Maciej Szczytowsko <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public class LogicalExpression implements Criterion {

    private final Criterion lhs;
    private final Criterion rhs;
    private final String operator;

    /**
     * Create new logical expression.
     *
     * @param lhs left critetion
     * @param rhs right criterion
     * @param operator operator
     */
    protected LogicalExpression(Criterion lhs, Criterion rhs, String operator) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.operator = operator;
    }

    @Override
    public String toSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
        return "(" + lhs.toSqlString(criteria, criteriaQuery) + " " + operator + " " + rhs.toSqlString(criteria, criteriaQuery) + ")";
    }
}
