package com.szczytowski.genericdao.api;

/**
 * Interface marks class which can be enabled and disabled.
 */
public interface IActivable {

	/**
	 * Property which represents active flag.
	 */
	String P_IS_ACTIVE = "isActive";
	
	/**
	 * Check if entity is active.
	 * 
	 * @return is active?
	 */
	boolean isActive();

	/**
	 * Set entity as active.
	 * 
	 * @param isActive
	 *            is entity active?
	 */
	void setActive(boolean isActive);

}
