/**
 * 
 */
package com.szczytowski.genericdao.exception;

import java.io.Serializable;

/**
 * @author Maciej Szczytowski <mszczytowski+java@gmail.com>
 *
 */
public class EntityLockedForDeletionException extends RuntimeException {

	private static final long serialVersionUID = -1207548630964235009L;

	private Class clazz;
	
	private Serializable id;
	
	/**
	 * Contructor.
	 */
	public EntityLockedForDeletionException(Class clazz, Serializable id) {
		this.clazz = clazz;
		this.id = id;
	}
	
	@Override
	public String getMessage() {
		return "Entity " + clazz.getSimpleName() + "#" + id.toString() + " can't be deleted";
	}
	
}
