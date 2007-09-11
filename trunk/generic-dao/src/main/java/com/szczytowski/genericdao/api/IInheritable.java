package com.szczytowski.genericdao.api;

/**
 * Interface marks class which is inheritable. There is unlimited number of
 * levels. Parent have the same type as child.
 * 
 * @param <T>
 *            parent's type, the same as child's type
 */
public interface IInheritable<T> {

	/**
	 * Property which represents parent.
	 */
	String P_PARENT = "parent";
	
	/**
	 * Get entity's parent.
	 * 
	 * @return parent
	 */
	T getParent();

	/**
	 * Set entity's parent. Null means no parent, root level entity.
	 * 
	 * @param parent
	 *            parent
	 */
	void setParent(T parent);

}
