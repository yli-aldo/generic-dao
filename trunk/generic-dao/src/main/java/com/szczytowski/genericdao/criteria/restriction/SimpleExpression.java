package com.szczytowski.genericdao.criteria.restriction;


import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Criterion;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class SimpleExpression implements Criterion {

	private final String propertyName;
	private final Object value;
	private final String op;

	protected SimpleExpression(String propertyName, Object value, String op) {
		this.propertyName = propertyName;
		this.value = value;
		this.op = op;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		criteriaQuery.setProperty(value);
		return criteriaQuery.getPropertyName(propertyName, criteria) + op + "?";
	}

}
