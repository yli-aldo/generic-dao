package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class PropertyProjection extends SimpleProjection {

	private String propertyName;

	protected PropertyProjection(String prop, boolean grouped) {
		this.propertyName = prop;
		this.grouped = grouped;
	}

	protected PropertyProjection(String prop) {
		this(prop, false);
	}

	public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery) throws GenericDaoException {
		return criteriaQuery.getPropertyName(propertyName, criteria);
	}

	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		if (!grouped) {
			return super.toGroupSqlString(criteria, criteriaQuery);
		} else {
			return criteriaQuery.getPropertyName(propertyName, criteria);
		}
	}

}
