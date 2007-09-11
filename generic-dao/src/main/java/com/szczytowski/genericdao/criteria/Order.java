package com.szczytowski.genericdao.criteria;

import com.szczytowski.genericdao.exception.GenericDaoException;

public class Order {

	private boolean ascending;
	private String propertyName;

	protected Order(String propertyName, boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws GenericDaoException {
		return propertyName + ( ascending ? " asc" : " desc" );
	}

	/**
	 * Ascending order
	 */
	public static Order asc(String propertyName) {
		return new Order(propertyName, true);
	}

	/**
	 * Descending order
	 */
	public static Order desc(String propertyName) {
		return new Order(propertyName, false);
	}

}
