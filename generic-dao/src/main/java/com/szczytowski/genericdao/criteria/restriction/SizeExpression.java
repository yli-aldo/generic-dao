package com.szczytowski.genericdao.criteria.restriction;


import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Criterion;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class SizeExpression implements Criterion {
	
	private final String propertyName;
	private final int size;
	private final String op;
	
	protected SizeExpression(String propertyName, int size, String op) {
		this.propertyName = propertyName;
		this.size = size;
		this.op = op;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		criteriaQuery.setProperty(size);
		return "? " + op + " (select count(*) from " + criteriaQuery.getPropertyName(propertyName, criteria) + ")";
	}

}
