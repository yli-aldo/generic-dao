package com.szczytowski.genericdao.criteria.restriction;

import java.util.ArrayList;
import java.util.List;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.CriteriaQuery;
import com.szczytowski.genericdao.criteria.Criteria.Criterion;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class Junction implements Criterion {

	private final List<Criterion> criteria = new ArrayList<Criterion>();
	private final String op;
	
	protected Junction(String op) {
		this.op = op;
	}
	
	public Junction add(Criterion criterion) {
		criteria.add(criterion);
		return this;
	}

	public String toSqlString(Criteria crit, CriteriaQuery criteriaQuery) throws GenericDaoException {
		if ( criteria.size()==0 ) return "1=1";

		String sql = "(";
		
		for(Criterion criterion: criteria) {
			if(sql.length() > 1) sql += " " + op + " ";
			sql += criterion.toSqlString(crit, criteriaQuery);
			
		}

		return sql += ")";
	}

}
