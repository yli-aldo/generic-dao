package com.szczytowski.genericdao.criteria.projection;

import java.util.ArrayList;
import java.util.List;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Projection;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class ProjectionList implements Projection {

	private List<Projection> elements = new ArrayList<Projection>();

	protected ProjectionList() {}

	public ProjectionList create() {
		return new ProjectionList();
	}

	public ProjectionList add(Projection proj) {
		elements.add(proj);
		return this;
	}

	public String toSqlString(Criteria criteria, int loc, CriteriaQuery criteriaQuery) throws GenericDaoException {
		String sql = "";
		for(Projection projection: elements) {
			if (sql.length() > 0) sql+= ", ";
			sql += projection.toSqlString(criteria, loc++, criteriaQuery);
		}
		return sql;
	}

	public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		String sql = "";
		for(Projection projection: elements) {
			if(projection.isGrouped()) {
				if (sql.length() > 0) sql+= ", ";
				sql += projection.toGroupSqlString(criteria, criteriaQuery);
			}
		}
		return sql;
	}

	public boolean isGrouped() {
		for(Projection projection: elements) {
			if(projection.isGrouped()) {
				return true;
			}
		}
		return false;
	}
}
