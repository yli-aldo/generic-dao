/**
 * 
 */
package com.szczytowski.genericdao.exception;

/**
 * @author Maciej Szczytowski <mszczytowski+java@gmail.com>
 *
 */
public class GenericDaoException extends RuntimeException {
	
	private static final long serialVersionUID = -8935712042044355058L;

	/**
	 * Contructor.
	 */
	public GenericDaoException(String message) {
		super(message);
	}
	
	/**
	 * Contructor.
	 */
	public GenericDaoException(Throwable throwable) {
		super(throwable);
	}
	
	/**
	 * Contructor.
	 */
	public GenericDaoException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
