package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Criterion;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class IdentifierEqExpression implements Criterion {

	private final Object value;

	protected IdentifierEqExpression(Object value) {
		this.value = value;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		criteriaQuery.setProperty(value);
		return criteriaQuery.getPropertyName(IEntity.P_ID, criteria) + " = ?";
	}

}
