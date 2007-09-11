package com.szczytowski.genericdao.test;

import java.io.Serializable;
import java.util.List;

import junit.framework.Assert;

import org.springframework.test.jpa.AbstractJpaTests;

import com.szczytowski.genericdao.api.IActivable;
import com.szczytowski.genericdao.api.IDao;
import com.szczytowski.genericdao.api.IDefaultable;
import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.api.IHiddenable;
import com.szczytowski.genericdao.api.IInheritable;
import com.szczytowski.genericdao.exception.EntityLockedForDeletionException;
import com.szczytowski.genericdao.exception.EntityNotFoundException;

/**
 * Abstract unit tests for {@link IDao} class.
 */
public class GenericDaoTest extends AbstractJpaTests /*AbstractTransactionalSpringContextTests*/ {

	private IDao complexDao;

	private IDao simpleDao;

	/**
	 * Constructor.
	 */
	public GenericDaoTest() {
		setAutowireMode(AUTOWIRE_BY_NAME);
	}

	/**
	 * Setter for complex dao.
	 * 
	 * @param complexDao
	 *            complex dao
	 * @see #getComplexEntity(String, boolean, boolean, boolean, IEntity)
	 */
	public void setComplexDao(IDao complexDao) {
		this.complexDao = complexDao;
	}

	/**
	 * Setter for simple dao.
	 * 
	 * @param simpleDao
	 *            simple dao
	 * @see #getSimpleEntity(String)
	 */
	public void setSimpleDao(IDao simpleDao) {
		this.simpleDao = simpleDao;
	}

	/**
	 * Entity which implements {@link IEntity}, {@link IHiddenable},
	 * {@link IInheritable}, {@link IActivable} and {@link IDefaultable}.
	 * 
	 * @param prop
	 *            example property
	 * @param isActive
	 *            is entity active
	 * @param isDefault
	 *            is entity default
	 * @param isHidden
	 *            is entity hidden
	 * @param parent
	 *            entity's parent
	 * @return new entity
	 */
	protected IEntity getComplexEntity(String prop, boolean isActive,
			boolean isDefault, boolean isHidden, IEntity parent) {
		return new ComplexEntityImpl(prop, isActive, isDefault, isHidden,
				(ComplexEntityImpl) parent);
	}

	/**
	 * Entity which implements only {@link IEntity}.
	 * 
	 * @param prop
	 *            example property
	 * @return new entity
	 */
	protected IEntity getSimpleEntity(String prop) {
		return new SimpleEntityImpl(prop);
	}

	/**
	 * Get spring configurations.
	 * 
	 * @return spring configurations
	 */
	protected String[] getConfigLocations() {
		return new String[] { "classpath:/com/szczytowski/genericdao/test/context.xml" };
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#load(java.io.Serializable)}.
	 */
	public void testLoad() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		complexDao.save(e1);

		IEntity e1t = complexDao.load(e1.getId());

		Assert.assertEquals(e1, e1t);
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#load(java.io.Serializable)}.
	 */
	public void testLoadException() {
		try {
			complexDao.load(0L);
			fail();
		} catch (EntityNotFoundException e) {
		}
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#get(java.io.Serializable)}.
	 */
	public void testGet() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);

		complexDao.save(e1);

		IEntity e1t = complexDao.get(e1.getId());

		Assert.assertEquals(e1, e1t);
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#get(java.io.Serializable)}.
	 */	
	public void testGetNull() {
		Assert.assertNull(complexDao.get(0L));
	}

	/**
	 * Test method for {@link com.szczytowski.genericdao.api.IDao#get(I[])}.
	 */	
	public void testGetIArray() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop2", true, false, false, null);
		IEntity e3 = getComplexEntity("prop3", true, true, true, e1);

		complexDao.save(new IEntity[] { e1, e2, e3 });

		List<IEntity> et = complexDao.get(new Serializable[] { e1.getId(),
				e2.getId(), e3.getId() });

		Assert.assertTrue(et.contains(e1));
		Assert.assertTrue(et.contains(e2));
		Assert.assertTrue(et.contains(e3));
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#get(com.szczytowski.genericdao.api.IInheritable)}.
	 */
	public void testGetInheritableOf() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop2", true, false, false, e1);
		IEntity e3 = getComplexEntity("prop3", true, true, true, e1);

		complexDao.save(new IEntity[] { e1, e2, e3 });

		List<IEntity> e1t = complexDao.get((IInheritable) e1);

		Assert.assertFalse(e1t.contains(e1));
		Assert.assertTrue(e1t.contains(e2));
		Assert.assertTrue(e1t.contains(e3));

		List<IEntity> e2t = complexDao.get((IInheritable) e2);

		Assert.assertEquals(e2t.size(), 0);
	}

	/**
	 * Test method for {@link com.szczytowski.genericdao.api.IDao#getAll()}.
	 */
	public void testGetAll() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop2", true, false, false, null);
		IEntity e3 = getComplexEntity("prop3", true, true, true, e1);

		complexDao.save(new IEntity[] { e1, e2, e3 });

		List<IEntity> et = complexDao.getAll();

		Assert.assertTrue(et.contains(e1));
		Assert.assertTrue(et.contains(e2));
		Assert.assertTrue(et.contains(e3));
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#findByExample(com.szczytowski.genericdao.api.IEntity)}.
	 */
	public void testFindByExample() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop1", true, false, false, e1);
		IEntity e3 = getComplexEntity("prop1", false, true, false, e1);
		IEntity e4 = getComplexEntity("prop2", false, false, true, null);

		complexDao.save(new IEntity[] { e1, e2, e3, e4 });

		List<IEntity> et1 = null;

		et1 = complexDao.findByExample(e1);

		Assert.assertTrue(et1.contains(e1));
		Assert.assertFalse(et1.contains(e2));
		Assert.assertFalse(et1.contains(e3));
		Assert.assertFalse(et1.contains(e4));

		et1 = complexDao.findByExample(e2);

		Assert.assertFalse(et1.contains(e1));
		Assert.assertTrue(et1.contains(e2));
		Assert.assertTrue(et1.contains(e3));
		Assert.assertFalse(et1.contains(e4));
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#setAsDafault(com.szczytowski.genericdao.api.IDefaultable)}.
	 */
	public void testSetAsDafault() {
		IEntity e1 = getComplexEntity("prop1", true, true, false, null);
		IEntity e2 = getComplexEntity("prop2", true, false, false, null);

		complexDao.save(new IEntity[] { e1, e2 });

		Assert.assertTrue(((IDefaultable) complexDao.get(e1.getId()))
				.isDefault());
		Assert.assertFalse(((IDefaultable) complexDao.get(e2.getId()))
				.isDefault());

		complexDao.setAsDafault((IDefaultable) e2);

		Assert.assertFalse(((IDefaultable) complexDao.get(e1.getId()))
				.isDefault());
		Assert.assertTrue(((IDefaultable) complexDao.get(e2.getId()))
				.isDefault());

		complexDao.setAsDafault((IDefaultable) e1);

		Assert.assertTrue(((IDefaultable) complexDao.get(e1.getId()))
				.isDefault());
		Assert.assertFalse(((IDefaultable) complexDao.get(e2.getId()))
				.isDefault());
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#save(com.szczytowski.genericdao.api.IEntity)}.
	 */
	public void testSave() {
		IEntity e1 = getComplexEntity("prop", false, false, false, null);

		complexDao.save(e1);

		IEntity e1t = complexDao.get(e1.getId());

		Assert.assertEquals(e1, e1t);
	}

	/**
	 * Test method for {@link com.szczytowski.genericdao.api.IDao#save(T[])}.
	 */
	public void testSaveArray() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop2", true, false, false, null);
		IEntity e3 = getComplexEntity("prop3", true, true, true, e1);

		complexDao.save(new IEntity[] { e1, e2, e3 });

		IEntity e1t = complexDao.get(e1.getId());
		IEntity e2t = complexDao.get(e2.getId());
		IEntity e3t = complexDao.get(e3.getId());

		Assert.assertEquals(e1, e1t);
		Assert.assertEquals(e2, e2t);
		Assert.assertEquals(e3, e3t);
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#delete(java.io.Serializable)}.
	 */
	public void testDeleteById() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getSimpleEntity("prop2");

		complexDao.save(e1);
		simpleDao.save(e2);

		Assert.assertFalse(((IHiddenable) complexDao.get(e1.getId()))
				.isHidden());
		Assert.assertNotNull(simpleDao.get(e2.getId()));

		complexDao.delete(e1.getId());
		simpleDao.delete(e2.getId());

		Assert
				.assertTrue(((IHiddenable) complexDao.get(e1.getId()))
						.isHidden());
		Assert.assertNull(simpleDao.get(e2.getId()));
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#delete(java.io.Serializable)}.
	 */
	public void testDeleteByIdException() {
		IEntity e1 = getComplexEntity("prop1", false, true, false, null);

		complexDao.save(e1);

		Assert.assertNotNull(complexDao.get(e1.getId()));

		try {
			complexDao.delete(e1.getId());
			fail("Cannot delete default entity");
		} catch (EntityLockedForDeletionException e) {
		}
	}

	/**
	 * Test method for {@link com.szczytowski.genericdao.api.IDao#delete(I[])}.
	 */
	public void testDeleteByIdArray() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop2", true, false, false, null);
		IEntity e3 = getComplexEntity("prop3", true, false, false, e1);
		IEntity e4 = getSimpleEntity("prop4");
		IEntity e5 = getSimpleEntity("prop5");

		complexDao.save(new IEntity[] { e1, e2, e3 });
		simpleDao.save(new IEntity[] { e4, e5 });

		Assert.assertFalse(((IHiddenable) complexDao.get(e1.getId()))
				.isHidden());
		Assert.assertFalse(((IHiddenable) complexDao.get(e2.getId()))
				.isHidden());
		Assert.assertFalse(((IHiddenable) complexDao.get(e3.getId()))
				.isHidden());
		Assert.assertNotNull(simpleDao.get(e4.getId()));
		Assert.assertNotNull(simpleDao.get(e5.getId()));

		complexDao.delete(new Serializable[] { e1.getId(), e2.getId(),
				e3.getId() });
		simpleDao.delete(new Serializable[] { e4.getId(), e5.getId() });

		Assert
				.assertTrue(((IHiddenable) complexDao.get(e1.getId()))
						.isHidden());
		Assert
				.assertTrue(((IHiddenable) complexDao.get(e2.getId()))
						.isHidden());
		Assert
				.assertTrue(((IHiddenable) complexDao.get(e3.getId()))
						.isHidden());
		Assert.assertNull(simpleDao.get(e4.getId()));
		Assert.assertNull(simpleDao.get(e5.getId()));
	}

	/**
	 * Test method for {@link com.szczytowski.genericdao.api.IDao#delete(I[])}.
	 */
	public void testDeleteByIdArrayException() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop2", true, true, false, null);

		complexDao.save(new IEntity[] { e1, e2 });

		Assert.assertFalse(((IHiddenable) complexDao.get(e1.getId()))
				.isHidden());
		Assert.assertFalse(((IHiddenable) complexDao.get(e2.getId()))
				.isHidden());

		try {
			complexDao.delete(new Serializable[] { e1.getId(), e2.getId() });
			fail("Cannot delete default entity");
		} catch (EntityLockedForDeletionException e) {
		}
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#delete(com.szczytowski.genericdao.api.IEntity)}.
	 */
	public void testDelete() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getSimpleEntity("prop2");

		complexDao.save(e1);
		simpleDao.save(e2);

		Assert.assertFalse(((IHiddenable) complexDao.get(e1.getId()))
				.isHidden());
		Assert.assertNotNull(simpleDao.get(e2.getId()));

		complexDao.delete(e1);
		simpleDao.delete(e2);

		Assert
				.assertTrue(((IHiddenable) complexDao.get(e1.getId()))
						.isHidden());
		Assert.assertNull(simpleDao.get(e2.getId()));
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#delete(com.szczytowski.genericdao.api.IEntity)}.
	 */
	public void testDeleteException() {
		IEntity e1 = getComplexEntity("prop1", false, true, false, null);

		complexDao.save(e1);

		Assert.assertNotNull(complexDao.get(e1.getId()));

		try {
			complexDao.delete(e1);
			fail("Cannot delete default entity");
		} catch (EntityLockedForDeletionException e) {
		}
	}

	/**
	 * Test method for {@link com.szczytowski.genericdao.api.IDao#delete(T[])}.
	 */
	public void testDeleteArray() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop2", true, false, false, null);
		IEntity e3 = getComplexEntity("prop3", true, false, false, e1);
		IEntity e4 = getSimpleEntity("prop4");
		IEntity e5 = getSimpleEntity("prop5");

		complexDao.save(new IEntity[] { e1, e2, e3 });
		simpleDao.save(new IEntity[] { e4, e5 });

		Assert.assertFalse(((IHiddenable) complexDao.get(e1.getId()))
				.isHidden());
		Assert.assertFalse(((IHiddenable) complexDao.get(e2.getId()))
				.isHidden());
		Assert.assertFalse(((IHiddenable) complexDao.get(e3.getId()))
				.isHidden());
		Assert.assertNotNull(simpleDao.get(e4.getId()));
		Assert.assertNotNull(simpleDao.get(e5.getId()));

		complexDao.delete(new IEntity[] { e1, e2, e3 });
		simpleDao.delete(new IEntity[] { e4, e5 });

		Assert
				.assertTrue(((IHiddenable) complexDao.get(e1.getId()))
						.isHidden());
		Assert
				.assertTrue(((IHiddenable) complexDao.get(e2.getId()))
						.isHidden());
		Assert
				.assertTrue(((IHiddenable) complexDao.get(e3.getId()))
						.isHidden());
		Assert.assertNull(simpleDao.get(e4.getId()));
		Assert.assertNull(simpleDao.get(e5.getId()));
	}

	/**
	 * Test method for {@link com.szczytowski.genericdao.api.IDao#delete(T[])}.
	 */
	public void testDeleteArrayException() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop2", true, true, false, null);

		complexDao.save(new IEntity[] { e1, e2 });

		Assert.assertFalse(((IHiddenable) complexDao.get(e1.getId()))
				.isHidden());
		Assert.assertFalse(((IHiddenable) complexDao.get(e2.getId()))
				.isHidden());

		try {
			complexDao.delete(new IEntity[] { e1, e2 });
			fail("Cannot delete default entity");
		} catch (EntityLockedForDeletionException e) {
		}
	}

	/**
	 * Test method for {@link com.szczytowski.genericdao.api.IDao#deleteAll()}.
	 */
	public void testDeleteAll() {
		IEntity e1 = getComplexEntity("prop1", false, false, false, null);
		IEntity e2 = getComplexEntity("prop2", true, false, false, null);
		IEntity e3 = getComplexEntity("prop3", true, true, false, e1);
		IEntity e4 = getSimpleEntity("prop3");

		complexDao.save(new IEntity[] { e1, e2, e3 });
		simpleDao.save(e4);

		Assert.assertFalse(((IHiddenable) complexDao.get(e1.getId()))
				.isHidden());
		Assert.assertFalse(((IHiddenable) complexDao.get(e2.getId()))
				.isHidden());
		Assert.assertFalse(((IHiddenable) complexDao.get(e3.getId()))
				.isHidden());
		Assert.assertNotNull(simpleDao.get(e4.getId()));

		complexDao.deleteAll();
		simpleDao.deleteAll();

		Assert
				.assertTrue(((IHiddenable) complexDao.get(e1.getId()))
						.isHidden());
		Assert
				.assertTrue(((IHiddenable) complexDao.get(e2.getId()))
						.isHidden());
		Assert
				.assertTrue(((IHiddenable) complexDao.get(e3.getId()))
						.isHidden());
		Assert.assertNull(simpleDao.get(e4.getId()));

	}

	/**
	 * Test method for {@link com.szczytowski.genericdao.api.IDao#isActivable()}.
	 */
	public void testIsActivable() {
		Assert.assertTrue(complexDao.isActivable());
		Assert.assertFalse(simpleDao.isActivable());
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#isDefaultable()}.
	 */
	public void testIsDefaultable() {
		Assert.assertTrue(complexDao.isDefaultable());
		Assert.assertFalse(simpleDao.isDefaultable());
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#isHiddenable()}.
	 */
	public void testIsHiddenable() {
		Assert.assertTrue(complexDao.isHiddenable());
		Assert.assertFalse(simpleDao.isHiddenable());
	}

	/**
	 * Test method for
	 * {@link com.szczytowski.genericdao.api.IDao#isInheritable()}.
	 */
	public void testIsInheritable() {
		Assert.assertTrue(complexDao.isInheritable());
		Assert.assertFalse(simpleDao.isHiddenable());
	}
}