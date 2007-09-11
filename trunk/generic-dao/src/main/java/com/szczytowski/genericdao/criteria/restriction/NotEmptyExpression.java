package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria.Criterion;

public class NotEmptyExpression extends AbstractEmptinessExpression implements Criterion {

	protected NotEmptyExpression(String propertyName) {
		super( propertyName );
	}

	protected boolean excludeEmpty() {
		return true;
	}

}
