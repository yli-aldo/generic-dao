package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Criterion;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class PropertyExpression implements Criterion {

	private final String propertyName;
	private final String otherPropertyName;
	private final String op;

	protected PropertyExpression(String propertyName, String otherPropertyName, String op) {
		this.propertyName = propertyName;
		this.otherPropertyName = otherPropertyName;
		this.op = op;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		return criteriaQuery.getPropertyName(propertyName, criteria) + op + criteriaQuery.getPropertyName(otherPropertyName, criteria);
	}

}
