package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class IdentifierProjection extends SimpleProjection {
	
	protected IdentifierProjection(boolean grouped) {
		this.grouped = grouped;
	}
	
	protected IdentifierProjection() {
		this(false);
	}
	
	public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery)  throws GenericDaoException {
		return criteriaQuery.getPropertyName(IEntity.P_ID, criteria);
	}
	
	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		if (!grouped) {
			return super.toGroupSqlString(criteria, criteriaQuery);
		} else {
			return criteriaQuery.getPropertyName(IEntity.P_ID, criteria);
		}
	}

}
