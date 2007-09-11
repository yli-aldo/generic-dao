package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class CountProjection extends AggregateProjection {

	private boolean distinct;

	protected CountProjection(String prop) {
		super("count", prop);
	}
	
	public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery) throws GenericDaoException {
		String sql = "count(";
		if (distinct) sql += "distinct ";
		return sql +=  criteriaQuery.getPropertyName(propertyName, criteria) + ")";
	}
	
	public CountProjection setDistinct() {
		distinct = true;
		return this;
	}
	
}
