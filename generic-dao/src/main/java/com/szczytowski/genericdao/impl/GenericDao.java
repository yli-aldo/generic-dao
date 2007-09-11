package com.szczytowski.genericdao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.szczytowski.genericdao.api.IActivable;
import com.szczytowski.genericdao.api.IDao;
import com.szczytowski.genericdao.api.IDefaultable;
import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.api.IHiddenable;
import com.szczytowski.genericdao.api.IInheritable;
import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.restriction.Restrictions;
import com.szczytowski.genericdao.exception.EntityLockedForDeletionException;
import com.szczytowski.genericdao.exception.EntityNotFoundException;

/**
 * Abstract implementation of generic DAO.
 * 
 * @param <T>
 *            entity type, it must implements at least {@link IEntity}
 * @param <I>
 *            entity's primary key, it must be serializable
 */
public class GenericDao<T extends IEntity<I>, I extends Serializable> implements IDao<T, I> {

	private EntityManager entityManager;
	
	private Class<IEntity<I>> clazz;

	private boolean isDefaultable;

	private boolean isActivable;

	private boolean isHiddenable;

	private boolean isInheritable;

	/**
	 * Default constructor. Use it if you extend this class.
	 */
	public GenericDao() {
		clazz = (Class<IEntity<I>>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		checkGenericClass();
	}

	/**
	 * Constructor with given {@link IEntity} implementation. Use it if you
	 * don't want to extend this class.
	 * 
	 * @param clazz
	 *            class with will be accessed by DAO methods
	 */
	@SuppressWarnings("unchecked")
	public GenericDao(Class clazz) {
		this.clazz = (Class<IEntity<I>>) clazz;
		checkGenericClass();
	}

	@SuppressWarnings("unchecked")
 	public T load(I id) {
		T entity = get(id);
		if(entity == null) {
			throw new EntityNotFoundException(clazz, id);
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public T get(I id) {
		return (T) entityManager.find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> get(I... ids) {
		return findByCriteria(Criteria.forClass(clazz).add(Restrictions.in(IEntity.P_ID, ids)));
	}

	@SuppressWarnings("unchecked")
	public List<T> get(IInheritable<T> parent) {
		if (parent == null) {
			return findByCriteria(Criteria.forClass(clazz).add(Restrictions.isNull(IInheritable.P_PARENT)));
		} else {
			return findByCriteria(Criteria.forClass(clazz).add(Restrictions.eq(IInheritable.P_PARENT, parent)));
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return findByCriteria(Criteria.forClass(clazz));
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T example) {
		Criteria criteria = Criteria.forClass(clazz);
			
		Field[] fields = example.getClass().getDeclaredFields();
		
		for(Field field: fields) {
			if(field.getName().equals(IEntity.P_ID)) continue;
			if(field.getName().equals(IActivable.P_IS_ACTIVE)) continue;
			if(field.getName().equals(IDefaultable.P_IS_DEFAULT)) continue;
			if(field.getName().equals(IHiddenable.P_IS_HIDDEN)) continue;
			if(!field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Basic.class)) continue;
			
			Object value = null;
			
			try {
				field.setAccessible(true);
				value = field.get(example);
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			}
			
			if(value == null) continue;
			
			criteria.add(Restrictions.eq(field.getName(), value));
		}

		if(example instanceof IHiddenable) {			
			if(((IInheritable)example).getParent() == null) {
				criteria.add(Restrictions.isNull(IInheritable.P_PARENT));
			} else {
				criteria.add(Restrictions.eq(IInheritable.P_PARENT, ((IInheritable)example).getParent()));
			}
		}
		
		return findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public void setAsDafault(IDefaultable object) {
		if (object.getExample() != null) {
			List<T> objects = findByExample((T) object.getExample());
			for (T o : objects) {
				if (object != o) {
					((IDefaultable) o).setDefault(false);
					entityManager.merge(o);
				}
			}
		}
		object.setDefault(true);
		
		if(((T)object).getId() != null) {
			entityManager.merge(object);
		} else {
			entityManager.persist(object);
		}
	}

	public void save(final T object) {
		if(object.getId() != null) {
			entityManager.merge(object);
		} else {
			entityManager.persist(object);
		}
	}

	public void save(final T... objects) {
		for(T object: objects) {
			if(object.getId() != null) {
				entityManager.merge(object);
			} else {
				entityManager.persist(object);
			}	
		}
	}

	public void delete(final I id) {
		delete(load(id));
	}

	public void delete(final I... ids) {
		deleteAll(get(ids), true);
	}

	public void delete(final T object) {
		if (isDefaultable) {
			checkIfDefault(object);
		}
		if (isHiddenable) {
			((IHiddenable) object).setHidden(true);
			entityManager.merge(object);
		} else {			
			entityManager.remove(object);
		}
	}

	public void delete(final T... objects) {
		deleteAll(Arrays.asList(objects), true);
	}

	public void deleteAll() {
		deleteAll(getAll(), false);
	}

	private void deleteAll(final Collection<T> objects, boolean checkIdDefault) {
		if (checkIdDefault) {
			if (isDefaultable) {
				for (T object : objects) {
					checkIfDefault(object);
				}
			}
		}
		if (isHiddenable) {
			for (T object : objects) {
				((IHiddenable) object).setHidden(true);
				entityManager.merge(object);
			}
		} else {
			for (T object : objects) {
				entityManager.remove(object);
			}
		}
	}

	private void checkIfDefault(T entity) {
		if (((IDefaultable) entity).isDefault()) {
			throw new EntityLockedForDeletionException(clazz, entity.getId());
		}
	}

	private void checkGenericClass() {		
		for (Class i : clazz.getInterfaces()) {
			if (i == IDefaultable.class)
				isDefaultable = true;
			else if (i == IActivable.class)
				isActivable = true;
			else if (i == IHiddenable.class)
				isHiddenable = true;
			else if (i == IInheritable.class)
				isInheritable = true;
		}
	}

	public void refresh(final T entity) {
		entityManager.refresh(entity);
	}
	
	public void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
	}
	
	/**
	 * Retrieve objects using criteria. It is equivalent to <code>criteria.list(entityManager)</code>.
	 * 
	 * @param criteria which will be executed
	 * @return list of founded objects
	 * @see Query#getResultList()
	 */
	@SuppressWarnings("unchecked")
	protected List findByCriteria(Criteria criteria) {
		return criteria.list(entityManager);
	}
	
	/**
	 * Retrieve an unique object using criteria. It is equivalent to <code>criteria.uniqueResult(entityManager)</code>.
	 * 
	 * @param criteria which will be executed
	 * @return founded object or null
	 * @throws NoResultException - if there is no result 
     * @throws NonUniqueResultException- if more than one result
     * @see Query#getSingleResult()
	 */
	protected Object findUniqueByCriteria(Criteria criteria) {
		return criteria.uniqueResult(entityManager);
	}

	public boolean isActivable() {
		return isActivable;
	}

	public boolean isDefaultable() {
		return isDefaultable;
	}

	public boolean isHiddenable() {
		return isHiddenable;
	}

	public boolean isInheritable() {
		return isInheritable;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
}
