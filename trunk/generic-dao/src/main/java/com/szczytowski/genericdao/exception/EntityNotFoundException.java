/**
 * 
 */
package com.szczytowski.genericdao.exception;

import java.io.Serializable;

/**
 * @author Maciej Szczytowski <mszczytowski+java@gmail.com>
 *
 */
public class EntityNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 3575345723136102507L;

	private Class clazz;
	
	private Serializable id;
	
	/**
	 * Contructor.
	 */
	public EntityNotFoundException(Class clazz, Serializable id) {
		this.clazz = clazz;
		this.id = id;
	}
	
	@Override
	public String getMessage() {
		return "Entity " + clazz.getSimpleName() + "#" + id.toString() + " hasn't been found.";
	}
	
}
