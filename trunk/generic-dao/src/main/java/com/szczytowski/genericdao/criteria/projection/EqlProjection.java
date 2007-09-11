package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class EqlProjection extends SimpleProjection {

	private String eql;
	
	private String groupEql;

	protected EqlProjection(String eql, String groupEql) {
		this.eql = eql;
		this.groupEql = groupEql;
		if(groupEql != null) {
			this.grouped = true;
		}
		
	}

	protected EqlProjection(String eql) {
		this(eql, null);
	}

	public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery) throws GenericDaoException {
		return eql;
	}

	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		if (!grouped) {
			return super.toGroupSqlString(criteria, criteriaQuery);
		} else {
			return groupEql;
		}
	}

}
