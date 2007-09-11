package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class RowCountProjection extends SimpleProjection {

	protected RowCountProjection() {}

	public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery) throws GenericDaoException {
		return "count(*)";
	}

}
