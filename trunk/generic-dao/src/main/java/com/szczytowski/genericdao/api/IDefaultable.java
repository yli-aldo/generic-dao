package com.szczytowski.genericdao.api;

/**
 * Interface marks class which have default flag. DAO checks if there is only
 * one default entity (using {@link #getExample()}).
 */
public interface IDefaultable {

	/**
	 * Property which represents default flag.
	 */
	String P_IS_DEFAULT = "isDefault";

	/**
	 * Check is entity is default.
	 * 
	 * @return is default?
	 */
	boolean isDefault();

	/**
	 * Set entity as default.
	 * 
	 * @param isDefault
	 *            is entity default one?
	 * @see #getExample()
	 */
	void setDefault(boolean isDefault);

	/**
	 * Get examplary entity, using in
	 * {@link IDao#setAsDafault(IDefaultable)} method.
	 * 
	 * Entity should have setted only this properties which are commons in group
	 * with one default entity, for example parent or category.
	 * 
	 * If method returns null any of entities won't be changed into not-default.
	 * 
	 * @return examplary entity
	 */
	IEntity getExample();

}
