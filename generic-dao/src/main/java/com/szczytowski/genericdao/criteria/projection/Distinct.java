package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Projection;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class Distinct implements Projection {

	private final Projection projection;

	public Distinct(Projection proj) {
		this.projection = proj;
	}

	public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery) throws GenericDaoException {
		return "distinct " + projection.toSqlString(criteria, position, criteriaQuery);
	}

	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		return projection.toGroupSqlString(criteria, criteriaQuery);
	}

	public boolean isGrouped() {
		return projection.isGrouped();
	}

}
