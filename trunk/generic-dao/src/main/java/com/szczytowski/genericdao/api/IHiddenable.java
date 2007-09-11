package com.szczytowski.genericdao.api;

/**
 * Interface marks class which cannot be deleted. If someone call DAO's delete
 * method entity will be hidden instead of delete.
 */
public interface IHiddenable {

	/**
	 * Property which represents hidden flag.
	 */
	String P_IS_HIDDEN = "isHidden";
	
	/**
	 * Check if entity is hidden.
	 * 
	 * @return is hidden?
	 */
	boolean isHidden();

	/**
	 * Set entity as hidden.
	 * 
	 * @param isHidden
	 *            is entity hidden?
	 */
	void setHidden(boolean isHidden);

}
