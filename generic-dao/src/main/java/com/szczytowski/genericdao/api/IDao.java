/**
 * 
 */
package com.szczytowski.genericdao.api;

import java.io.Serializable;
import java.util.List;

import com.szczytowski.genericdao.exception.EntityLockedForDeletionException;
import com.szczytowski.genericdao.exception.EntityNotFoundException;

/**
 * Interface represents implementation of generic DAO.
 *
 * @param <T> entity's type, it must implements at least {@link IEntity}
 * @param <I> entity's primary key
 */
public interface IDao<T extends IEntity<I>, I extends Serializable> {

	/**
	 * Retrieve an object that was previously persisted to the database using the indicated id as primary key.
	 * 
	 * @param id
	 *            object's pk
	 * @return object
	 * @throws EntityNotFoundException -
	 *             if not found
	 */
	T load(I id);

	/**
	 * Retrieve an object that was previously persisted to the database using the indicated id as primary key. Returns null if not found.
	 * 
	 * @param id
	 *            object's pk
	 * @return object
	 */
	T get(I id);

	/**
	 * Retrieve objects that were previously persisted to the database using the indicated ids as primary keys.
	 * 
	 * @param ids
	 *            objects's ids
	 * @return list of objects
	 */
	List<T> get(I... ids);
	
	/**
	 * Retrieve all objects with given parents that were previously persisted to the database.
	 * 
	 * If parent is null, method returns root objects.
	 * 
	 * @param parent
	 *            objects' parent
	 * @return list of objects
	 */
	List<T> get(IInheritable<T> parent);

	/**
	 * Retrieve all objects that were previously persisted to the database.
	 * 
	 * @return list of objects
	 */
	List<T> getAll();

	/**
	 * Find objects by example. Properties {@link IEntity#getId()}, {@link IDefaultable#isDefault()}, {@link IActivable#isActive()} and {@link IHiddenable#isHidden()} are ignored.
	 * 
	 * @param example
	 *            examplary object
	 * @return list of objects
	 */
	List<T> findByExample(T example);

	/**
	 * Set object as default.
	 * 
	 * Check if there is only one default object using
	 * {@link IDefaultable#getExample()} and {@link #findByExample(IEntity)} to
	 * get objects and mark them as not-default. It is possible to have more
	 * than one default object manipulating {@link IDefaultable#getExample()}
	 * method.
	 * 
	 * @param object
	 *            default object
	 */
	void setAsDafault(IDefaultable entity);
	
	/**
	 * Save changes made to a persistent object.
	 * 
	 * @param object
	 *            object
	 */
	void save(T entity);

	/**
	 * Save changes made to persistent objects.
	 * 
	 * @param objects objects
	 */
	void save(T... entities);

	/**
	 * Remove an object by given id from persistent storage in the database. Check if object is not default one.
	 * 
	 * If entity implements {@link IHiddenable} then it is hidden instead of delete.
	 * 
	 * @param id
	 *            object's pk
	 * @throws EntityLockedForDeletionException
	 */
	void delete(I id);

	/**
	 * Remove objects by given ids from persistent storage in the database. Check if all objects are not mark as default one.
	 * 
	 * If objects implement {@link IHiddenable} then it is hidden instead of delete.
	 * 
	 * @param ids
	 *            objects's pk
	 * @throws EntityLockedForDeletionException
	 */
	void delete(I... ids);

	/**
	 * Remove an object from persistent storage in the database. Check if object is not default one.
	 * 
	 * If object implements {@link IHiddenable} then it is hidden instead of delete.
	 * 
	 * @param object
	 *            object
	 * @throws EntityLockedForDeletionException
	 */
	void delete(T entity);

	/**
	 * Remove objects from persistent storage in the database. Check if all entities are not mark as default one.
	 * 
	 * If objects implement {@link IHiddenable} then it is hidden instead of delete.
	 * 
	 * @param objects
	 *            objects
	 * @throws EntityLockedForDeletionException
	 */
	void delete(T... entities);

	/**
	 * Delete all objects from persistent storage in the database, including default one.
	 * 
	 * If objects implement {@link IHiddenable} then it is hidden instead of delete.
	 */
	void deleteAll();
	
	/**
	 * Refresh a persistant object that may have changed in another thread/transaction.
	 * 
	 * @param entity
	 *            transient entity
	 */
	void refresh(T entity);
	
	/**
     * Write anything to database that is pending operation and clear it.
     */
    void flushAndClear();
	
	/**
	 * Check whether the DAO represents {@link IActivable} implementation.
	 * 
	 * @return is entity activable
	 */
	public boolean isActivable();

	/**
	 * Check whether the DAO represents {@link IDefaultable} implementation.
	 * 
	 * @return is entity defaultable
	 */
	public boolean isDefaultable();

	/**
	 * Check whether the DAO represents {@link IHiddenable} implementation.
	 * 
	 * @return is entity hiddenable
	 */
	public boolean isHiddenable();

	/**
	 * Check whether the DAO represents {@link IInheritable} implementation.
	 * 
	 * @return is entity inhertable
	 */
	public boolean isInheritable();

}
