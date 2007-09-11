package com.szczytowski.genericdao.criteria.projection;

import com.szczytowski.genericdao.criteria.Criteria.Projection;

public final class Projections {

	private Projections() {}
	
	/**
	 * Create a distinct projection from a projection
	 */
	public static Projection distinct(Projection proj) {
		return new Distinct(proj);
	}
	
	/**
	 * Create a new projection list
	 */
	public static ProjectionList projectionList() {
		return new ProjectionList();
	}
		
	/**
	 * The query row count, ie. <tt>count(*)</tt>
	 */
	public static Projection rowCount() {
		return new RowCountProjection();
	}
	
	/**
	 * A property value count
	 */
	public static Projection count(String propertyName) {
		return new CountProjection(propertyName);
	}
	
	/**
	 * A distinct property value count
	 */
	public static Projection countDistinct(String propertyName) {
		return new CountProjection(propertyName).setDistinct();
	}
	
	/**
	 * A property maximum value
	 */
	public static Projection max(String propertyName) {
		return new AggregateProjection("max", propertyName);
	}
	
	/**
	 * A property minimum value
	 */
	public static Projection min(String propertyName) {
		return new AggregateProjection("min", propertyName);
	}
	
	/**
	 * A property average value
	 */
	public static Projection avg(String propertyName) {
		return new AggregateProjection("avg", propertyName);
	}
	
	/**
	 * A property value sum
	 */
	public static Projection sum(String propertyName) {
		return new AggregateProjection("sum", propertyName);
	}
	
	/**
	 * A grouping property value
	 */
	public static Projection groupProperty(String propertyName) {
		return new PropertyProjection(propertyName, true);
	}
	
	/**
	 * A projected property value
	 */
	public static Projection property(String propertyName) {
		return new PropertyProjection(propertyName);
	}
	
	/**
	 * A grouping EQL projection, specifying both select clause and group by clause fragments
	 */
	public static Projection groupEql(String eql, String groupEql) {
		return new EqlProjection(eql, groupEql);
	}
	
	/**
	 * A EQL projection, a typed select clause fragment
	 */
	public static Projection eql(String eql) {
		return new EqlProjection(eql);
	}
	
	/**
	 * A projected identifier value
	 */
	public static Projection id() {
		return new IdentifierProjection();
	}

}
