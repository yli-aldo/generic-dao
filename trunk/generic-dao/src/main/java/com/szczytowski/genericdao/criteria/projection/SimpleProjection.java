package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Projection;

/**
 * Abstract projection.
 *
 * @author Maciej Szczytowsko <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public abstract class SimpleProjection implements Projection {

    /**
     * Group flag, true when this projection is grouping one.
     */
    protected boolean grouped;

    @Override
    public String toGroupSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
        throw new UnsupportedOperationException("not a grouping projection");
    }

    @Override
    public boolean isGrouped() {
        return grouped;
    }
}
