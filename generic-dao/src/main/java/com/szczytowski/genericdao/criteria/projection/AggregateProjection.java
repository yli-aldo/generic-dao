package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class AggregateProjection extends SimpleProjection {

	protected final String propertyName;

	private final String aggregate;

	protected AggregateProjection(String aggregate, String propertyName) {
		this.aggregate = aggregate;
		this.propertyName = propertyName;
	}

	public String toSqlString(Criteria criteria, int loc, CriteriaQuery criteriaQuery) throws GenericDaoException {
		return aggregate + "(" + criteriaQuery.getPropertyName(propertyName, criteria) + ")";
	}

}
