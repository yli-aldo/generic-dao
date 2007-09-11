package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Criterion;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class BetweenExpression implements Criterion {

	private final String propertyName;
	private final Object lo;
	private final Object hi;

	protected BetweenExpression(String propertyName, Object lo, Object hi) {
		this.propertyName = propertyName;
		this.lo = lo;
		this.hi = hi;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		criteriaQuery.setProperty(lo);
		criteriaQuery.setProperty(hi);
		return criteriaQuery.getPropertyName(propertyName, criteria) + " between ? and ?";
	}

}
