package com.szczytowski.genericdao.api;

import java.io.Serializable;

/**
 * Interface marks class which is an entity.
 * 
 * @param <I>
 *            type of entity primary key, it must be serializable
 */
public interface IEntity<I extends Serializable> extends Serializable {

	/**
	 * Property which represents id.
	 */
	String P_ID = "id";

	/**
	 * Get entity's primary key.
	 * 
	 * @return entity's pk
	 */
	I getId();

	/**
	 * Set entity's primary key.
	 * 
	 * @param id
	 *            entity's pk
	 */
	void setId(I id);

}
