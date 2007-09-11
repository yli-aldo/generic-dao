package com.szczytowski.genericdao.test.criteria;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;

import org.springframework.test.jpa.AbstractJpaTests;

import com.szczytowski.genericdao.api.IActivable;
import com.szczytowski.genericdao.api.IDao;
import com.szczytowski.genericdao.api.IDefaultable;
import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.api.IHiddenable;
import com.szczytowski.genericdao.api.IInheritable;
import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.exception.EntityLockedForDeletionException;
import com.szczytowski.genericdao.exception.EntityNotFoundException;

/**
 * Abstract unit tests for {@link IDao} class.
 */
public class CriteriaDaoTest extends AbstractJpaTests /* AbstractTransactionalSpringContextTests */{

	private IDao entity1Dao;

	private IDao entity2Dao;

	/**
	 * Constructor.
	 */
	public CriteriaDaoTest() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		setDependencyCheck(false);
	}

	public void setComplexDao(IDao complexDao) {
		this.entity1Dao = complexDao;
	}

	public void setSimpleDao(IDao simpleDao) {
		this.entity2Dao = simpleDao;
	}

	/**
	 * Get spring configurations.
	 * 
	 * @return spring configurations
	 */
	protected String[] getConfigLocations() {
		return new String[] { "classpath:/com/szczytowski/genericdao/test/context.xml" };
	}
	
	public void testMock() {
		
	}

}