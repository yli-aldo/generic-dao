package com.szczytowski.genericdao.criteria.restriction;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.szczytowski.genericdao.criteria.Criteria.Criterion;

public class Restrictions {

	Restrictions() {}

	/**
	 * Apply an "equal" constraint to the identifier property
	 */
	public static Criterion idEq(Object value) {
		return new IdentifierEqExpression(value);
	}
	/**
	 * Apply an "equal" constraint to the named property
	 */
	public static Criterion eq(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, "=");
	}
	/**
	 * Apply a "not equal" constraint to the named property
	 */
	public static Criterion ne(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, "<>");
	}
	/**
	 * Apply a "like" constraint to the named property
	 */
	public static Criterion like(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, " like ");
	}
	/**
	 * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
	 * operator
	 */
	public static Criterion ilike(String propertyName, Object value) {
		return new IlikeExpression(propertyName, value);
	}
	/**
	 * Apply a "greater than" constraint to the named property
	 */
	public static Criterion gt(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, ">");
	}
	/**
	 * Apply a "less than" constraint to the named property
	 */
	public static Criterion lt(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, "<");
	}
	/**
	 * Apply a "less than or equal" constraint to the named property
	 */
	public static Criterion le(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, "<=");
	}
	/**
	 * Apply a "greater than or equal" constraint to the named property
	 */
	public static Criterion ge(String propertyName, Object value) {
		return new SimpleExpression(propertyName, value, ">=");
	}
	/**
	 * Apply a "between" constraint to the named property
	 */
	public static Criterion between(String propertyName, Object lo, Object hi) {
		return new BetweenExpression(propertyName, lo, hi);
	}
	/**
	 * Apply an "in" constraint to the named property
	 */
	public static Criterion in(String propertyName, Object[] values) {
		return new InExpression(propertyName, values);
	}
	/**
	 * Apply an "in" constraint to the named property
	 */
	public static Criterion in(String propertyName, Collection values) {
		return new InExpression( propertyName, values.toArray() );
	}
	/**
	 * Apply an "is null" constraint to the named property
	 */
	public static Criterion isNull(String propertyName) {
		return new NullExpression(propertyName);
	}
	/**
	 * Apply an "equal" constraint to two properties
	 */
	public static Criterion eqProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, "=");
	}
	/**
	 * Apply a "not equal" constraint to two properties
	 */
	public static Criterion neProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, "<>");
	}
	/**
	 * Apply a "less than" constraint to two properties
	 */
	public static Criterion ltProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, "<");
	}
	/**
	 * Apply a "less than or equal" constraint to two properties
	 */
	public static Criterion leProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, "<=");
	}
	/**
	 * Apply a "greater than" constraint to two properties
	 */
	public static Criterion gtProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, ">");
	}
	/**
	 * Apply a "greater than or equal" constraint to two properties
	 */
	public static Criterion geProperty(String propertyName, String otherPropertyName) {
		return new PropertyExpression(propertyName, otherPropertyName, ">=");
	}
	/**
	 * Apply an "is not null" constraint to the named property
	 */
	public static Criterion isNotNull(String propertyName) {
		return new NotNullExpression(propertyName);
	}
	/**
	 * Return the conjuction of two expressions
	 */
	public static Criterion and(Criterion lhs, Criterion rhs) {
		return new LogicalExpression(lhs, rhs, "and");
	}
	/**
	 * Return the disjuction of two expressions
	 */
	public static Criterion or(Criterion lhs, Criterion rhs) {
		return new LogicalExpression(lhs, rhs, "or");
	}
	/**
	 * Return the negation of an expression
	 */
	public static Criterion not(Criterion expression) {
		return new NotExpression(expression);
	}

	/**
	 * Group expressions together in a single conjunction (A and B and C...)
	 */
	public static Conjunction conjunction() {
		return new Conjunction();
	}

	/**
	 * Group expressions together in a single disjunction (A or B or C...)
	 *
	 * @return Conjunction
	 */
	public static Disjunction disjunction() {
		return new Disjunction();
	}

	/**
	 * Apply an "equals" constraint to each property in the
	 * key set of a <tt>Map</tt>
	 */
	public static Criterion allEq(Map propertyNameValues) {
		Conjunction conj = conjunction();
		Iterator iter = propertyNameValues.entrySet().iterator();
		while ( iter.hasNext() ) {
			Map.Entry me = (Map.Entry) iter.next();
			conj.add( eq( (String) me.getKey(), me.getValue() ) );
		}
		return conj;
	}
	
	/**
	 * Constrain a collection valued property to be empty
	 */
	public static Criterion isEmpty(String propertyName) {
		return new EmptyExpression(propertyName);
	}

	/**
	 * Constrain a collection valued property to be non-empty
	 */
	public static Criterion isNotEmpty(String propertyName) {
		return new NotEmptyExpression(propertyName);
	}
	
	/**
	 * Constrain a collection valued property by size
	 */
	public static Criterion sizeEq(String propertyName, int size) {
		return new SizeExpression(propertyName, size, "=");
	}
	
	/**
	 * Constrain a collection valued property by size
	 */
	public static Criterion sizeNe(String propertyName, int size) {
		return new SizeExpression(propertyName, size, "<>");
	}
	
	/**
	 * Constrain a collection valued property by size
	 */
	public static Criterion sizeGt(String propertyName, int size) {
		return new SizeExpression(propertyName, size, "<");
	}
	
	/**
	 * Constrain a collection valued property by size
	 */
	public static Criterion sizeLt(String propertyName, int size) {
		return new SizeExpression(propertyName, size, ">");
	}
	
	/**
	 * Constrain a collection valued property by size
	 */
	public static Criterion sizeGe(String propertyName, int size) {
		return new SizeExpression(propertyName, size, "<=");
	}
	
	/**
	 * Constrain a collection valued property by size
	 */
	public static Criterion sizeLe(String propertyName, int size) {
		return new SizeExpression(propertyName, size, ">=");
	}
		
}
