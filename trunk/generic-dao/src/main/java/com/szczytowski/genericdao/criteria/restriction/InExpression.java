package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Criterion;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class InExpression implements Criterion {

	private final String propertyName;
	private final Object[] values;

	protected InExpression(String propertyName, Object[] values) {
		this.propertyName = propertyName;
		this.values = values;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		String sql = criteriaQuery.getPropertyName(propertyName, criteria) + " in (";
		
		for(Object v: values) {
			criteriaQuery.setProperty(v);
			sql += "?,";
		}
		
		return sql.substring(0, sql.length()-1) + ")";
	}

}
