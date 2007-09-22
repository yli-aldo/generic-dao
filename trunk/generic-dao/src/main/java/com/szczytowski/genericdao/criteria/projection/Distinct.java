package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Projection;

/**
 * Distinct projection.
 *
 * @author Maciej Szczytowski <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public class Distinct implements Projection {

    private final Projection projection;

    /**
     * Create new distincted projection using given one.
     * @param projection projection
     */
    public Distinct(Projection projection) {
        this.projection = projection;
    }

    @Override
    public String toSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
        return "distinct " + projection.toSqlString(criteria, criteriaQuery);
    }

    @Override
    public String toGroupSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
        return projection.toGroupSqlString(criteria, criteriaQuery);
    }

    @Override
    public boolean isGrouped() {
        return projection.isGrouped();
    }
}
