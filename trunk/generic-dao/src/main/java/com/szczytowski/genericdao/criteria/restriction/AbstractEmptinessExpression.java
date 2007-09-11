package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Criterion;
import com.szczytowski.genericdao.exception.GenericDaoException;

public abstract class AbstractEmptinessExpression implements Criterion {

	protected final String propertyName;

	protected AbstractEmptinessExpression(String propertyName) {
		this.propertyName = propertyName;
	}

	protected abstract boolean excludeEmpty();

	public final String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		return (excludeEmpty() ? "exists" : "not exists") + " (select 1 from " + criteriaQuery.getPropertyName(propertyName, criteria) + ")";
	}
	
}
