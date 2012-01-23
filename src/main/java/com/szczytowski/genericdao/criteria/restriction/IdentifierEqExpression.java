package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Criterion;

/**
 * Identifier expression.
 *
 * @author Maciej Szczytowski <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public class IdentifierEqExpression implements Criterion {

    private final Object value;

    /**
     * Create new identifier expression.
     *
     * @param value value
     */
    protected IdentifierEqExpression(Object value) {
        this.value = value;
    }

    @Override
    public String toSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
        criteriaQuery.setParam(value);
        return criteriaQuery.getPropertyName(IEntity.P_ID, criteria) + " = ?";
    }
}
