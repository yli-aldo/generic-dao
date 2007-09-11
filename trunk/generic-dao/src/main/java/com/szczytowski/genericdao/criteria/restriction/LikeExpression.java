package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Criterion;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class LikeExpression implements Criterion {

	private final String propertyName;
	private final Object value;
	private final Character escapeChar;
	private final boolean ignoreCase;

	protected LikeExpression(String propertyName, String value,	Character escapeChar, boolean ignoreCase) {
		this.propertyName = propertyName;
		this.value = value;
		this.escapeChar = escapeChar;
		this.ignoreCase = ignoreCase;
	}

	protected LikeExpression(String propertyName, String value) {
		this( propertyName, value, null, false );
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		criteriaQuery.setProperty(value);
		String lhs = ignoreCase ? "lowercase(" + criteriaQuery.getPropertyName(propertyName, criteria) + ")" : criteriaQuery.getPropertyName(propertyName, criteria);
		return lhs + " like ?" + ( escapeChar == null ? "" : " escape \'" + escapeChar + "\'" );

	}
}
