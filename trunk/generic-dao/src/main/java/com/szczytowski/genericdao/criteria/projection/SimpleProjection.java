package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Projection;
import com.szczytowski.genericdao.exception.GenericDaoException;

public abstract class SimpleProjection implements Projection {

	protected boolean grouped;
	
	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		throw new UnsupportedOperationException("not a grouping projection");
	}

	public boolean isGrouped() {
		return grouped;
	}

}
