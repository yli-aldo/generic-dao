package com.szczytowski.genericdao.test.dao;

import com.szczytowski.genericdao.test.*;
import java.util.List;
import junit.framework.Assert;
import javax.persistence.EntityNotFoundException;
import com.szczytowski.genericdao.api.IDefaultable;
import com.szczytowski.genericdao.api.IHiddenable;
import com.szczytowski.genericdao.api.IInheritable;

/**
 * Abstract unit tests for {@link IDao} class.
 */
public class GenericDaoTest extends AbstractTest {

    /**
     * Constructor.
     */
    public GenericDaoTest() {
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#load(java.io.Serializable)}.
     */
    public void testLoad() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        getComplexDao().save(e1);

        ComplexEntityImpl e1t = getComplexDao().load(e1.getId());

        Assert.assertEquals(e1, e1t);
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#load(java.io.Serializable)}.
     */
    public void testLoadException() {
        try {
            getComplexDao().load(0L);
            fail();
        } catch (EntityNotFoundException e) {
        }
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#get(java.io.Serializable)}.
     */
    public void testGet() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);

        getComplexDao().save(e1);

        ComplexEntityImpl e1t = getComplexDao().get(e1.getId());

        Assert.assertEquals(e1, e1t);
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#get(java.io.Serializable)}.
     */
    public void testGetNull() {
        Assert.assertNull(getComplexDao().get(0L));
    }

    /**
     * Test method for {@link com.szczytowski.genericdao.api.IDao#get(I[])}.
     */
    public void testGetIArray() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, false, false, null);
        ComplexEntityImpl e3 = getComplexEntity("prop3", true, true, true, e1);

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2, e3});

        List<ComplexEntityImpl> et = getComplexDao().get(new Long[]{e1.getId(), e2.getId(), e3.getId()});

        Assert.assertTrue(et.contains(e1));
        Assert.assertTrue(et.contains(e2));
        Assert.assertTrue(et.contains(e3));
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#get(com.szczytowski.genericdao.api.IInheritable)}.
     */
    public void testGetInheritableOf() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, false, false, e1);
        ComplexEntityImpl e3 = getComplexEntity("prop3", true, true, true, e1);

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2, e3});

        List<ComplexEntityImpl> e1t = getComplexDao().get((IInheritable) e1);

        Assert.assertFalse(e1t.contains(e1));
        Assert.assertTrue(e1t.contains(e2));
        Assert.assertTrue(e1t.contains(e3));

        List<ComplexEntityImpl> e2t = getComplexDao().get((IInheritable) e2);

        Assert.assertEquals(e2t.size(), 0);
    }

    /**
     * Test method for {@link com.szczytowski.genericdao.api.IDao#getAll()}.
     */
    public void testGetAll() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, false, false, null);
        ComplexEntityImpl e3 = getComplexEntity("prop3", true, true, true, e1);

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2, e3});

        List<ComplexEntityImpl> et = getComplexDao().getAll();

        Assert.assertTrue(et.contains(e1));
        Assert.assertTrue(et.contains(e2));
        Assert.assertTrue(et.contains(e3));
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#findByExample(com.szczytowski.genericdao.api.ComplexEntityImpl)}.
     */
    public void testFindByExample() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop1", true, false, false, e1);
        ComplexEntityImpl e3 = getComplexEntity("prop1", false, true, false, e1);
        ComplexEntityImpl e4 = getComplexEntity("prop2", false, false, true, null);

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2, e3, e4});

        List<ComplexEntityImpl> et1 = null;

        et1 = getComplexDao().findByExample(e1);

        Assert.assertTrue(et1.contains(e1));
        Assert.assertFalse(et1.contains(e2));
        Assert.assertFalse(et1.contains(e3));
        Assert.assertFalse(et1.contains(e4));

        et1 = getComplexDao().findByExample(e2);

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
        ComplexEntityImpl e1 = getComplexEntity("prop1", true, true, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, false, false, null);

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2});

        Assert.assertTrue(((IDefaultable) getComplexDao().get(e1.getId())).isDefault());
        Assert.assertFalse(((IDefaultable) getComplexDao().get(e2.getId())).isDefault());

        getComplexDao().setAsDefault((IDefaultable) e2);

        Assert.assertFalse(((IDefaultable) getComplexDao().get(e1.getId())).isDefault());
        Assert.assertTrue(((IDefaultable) getComplexDao().get(e2.getId())).isDefault());

        getComplexDao().setAsDefault((IDefaultable) e1);

        Assert.assertTrue(((IDefaultable) getComplexDao().get(e1.getId())).isDefault());
        Assert.assertFalse(((IDefaultable) getComplexDao().get(e2.getId())).isDefault());
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#save(com.szczytowski.genericdao.api.ComplexEntityImpl)}.
     */
    public void testSave() {
        ComplexEntityImpl e1 = getComplexEntity("prop", false, false, false, null);

        getComplexDao().save(e1);

        ComplexEntityImpl e1t = getComplexDao().get(e1.getId());

        Assert.assertEquals(e1, e1t);
    }

    /**
     * Test method for {@link com.szczytowski.genericdao.api.IDao#save(T[])}.
     */
    public void testSaveArray() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, false, false, null);
        ComplexEntityImpl e3 = getComplexEntity("prop3", true, true, true, e1);

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2, e3});

        ComplexEntityImpl e1t = getComplexDao().get(e1.getId());
        ComplexEntityImpl e2t = getComplexDao().get(e2.getId());
        ComplexEntityImpl e3t = getComplexDao().get(e3.getId());

        Assert.assertEquals(e1, e1t);
        Assert.assertEquals(e2, e2t);
        Assert.assertEquals(e3, e3t);
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#delete(java.io.Serializable)}.
     */
    public void testDeleteById() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        SimpleEntityImpl e2 = getSimpleEntity("prop2");

        getComplexDao().save(e1);
        getSimpleDao().save(e2);

        Assert.assertFalse(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertNotNull(getSimpleDao().get(e2.getId()));

        getComplexDao().delete(e1.getId());
        getSimpleDao().delete(e2.getId());

        Assert.assertTrue(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertNull(getSimpleDao().get(e2.getId()));
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#delete(java.io.Serializable)}.
     */
    public void testDeleteByIdException() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, true, false, null);

        getComplexDao().save(e1);

        Assert.assertNotNull(getComplexDao().get(e1.getId()));

        try {
            getComplexDao().delete(e1.getId());
            fail("Cannot delete default entity");
        } catch (UnsupportedOperationException e) {
        }
    }

    /**
     * Test method for {@link com.szczytowski.genericdao.api.IDao#delete(I[])}.
     */
    public void testDeleteByIdArray() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, false, false, null);
        ComplexEntityImpl e3 = getComplexEntity("prop3", true, false, false, e1);
        SimpleEntityImpl e4 = getSimpleEntity("prop4");
        SimpleEntityImpl e5 = getSimpleEntity("prop5");

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2, e3});
        getSimpleDao().save(new SimpleEntityImpl[]{e4, e5});

        Assert.assertFalse(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertFalse(((IHiddenable) getComplexDao().get(e2.getId())).isHidden());
        Assert.assertFalse(((IHiddenable) getComplexDao().get(e3.getId())).isHidden());
        Assert.assertNotNull(getSimpleDao().get(e4.getId()));
        Assert.assertNotNull(getSimpleDao().get(e5.getId()));

        getComplexDao().delete(new Long[]{e1.getId(), e2.getId(), e3.getId()});
        getSimpleDao().delete(new Long[]{e4.getId(), e5.getId()});

        Assert.assertTrue(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertTrue(((IHiddenable) getComplexDao().get(e2.getId())).isHidden());
        Assert.assertTrue(((IHiddenable) getComplexDao().get(e3.getId())).isHidden());
        Assert.assertNull(getSimpleDao().get(e4.getId()));
        Assert.assertNull(getSimpleDao().get(e5.getId()));
    }

    /**
     * Test method for {@link com.szczytowski.genericdao.api.IDao#delete(I[])}.
     */
    public void testDeleteByIdArrayException() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, true, false, null);

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2});

        Assert.assertFalse(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertFalse(((IHiddenable) getComplexDao().get(e2.getId())).isHidden());

        try {
            getComplexDao().delete(new Long[]{e1.getId(), e2.getId()});
            fail("Cannot delete default entity");
        } catch (UnsupportedOperationException e) {
        }
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#delete(com.szczytowski.genericdao.api.ComplexEntityImpl)}.
     */
    public void testDelete() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        SimpleEntityImpl e2 = getSimpleEntity("prop2");

        getComplexDao().save(e1);
        getSimpleDao().save(e2);

        Assert.assertFalse(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertNotNull(getSimpleDao().get(e2.getId()));

        getComplexDao().delete(e1);
        getSimpleDao().delete(e2);

        Assert.assertTrue(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertNull(getSimpleDao().get(e2.getId()));
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#delete(com.szczytowski.genericdao.api.ComplexEntityImpl)}.
     */
    public void testDeleteException() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, true, false, null);

        getComplexDao().save(e1);

        Assert.assertNotNull(getComplexDao().get(e1.getId()));

        try {
            getComplexDao().delete(e1);
            fail("Cannot delete default entity");
        } catch (UnsupportedOperationException e) {
        }
    }

    /**
     * Test method for {@link com.szczytowski.genericdao.api.IDao#delete(T[])}.
     */
    public void testDeleteArray() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, false, false, null);
        ComplexEntityImpl e3 = getComplexEntity("prop3", true, false, false, e1);
        SimpleEntityImpl e4 = getSimpleEntity("prop4");
        SimpleEntityImpl e5 = getSimpleEntity("prop5");

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2, e3});
        getSimpleDao().save(new SimpleEntityImpl[]{e4, e5});

        Assert.assertFalse(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertFalse(((IHiddenable) getComplexDao().get(e2.getId())).isHidden());
        Assert.assertFalse(((IHiddenable) getComplexDao().get(e3.getId())).isHidden());
        Assert.assertNotNull(getSimpleDao().get(e4.getId()));
        Assert.assertNotNull(getSimpleDao().get(e5.getId()));

        getComplexDao().delete(new ComplexEntityImpl[]{e1, e2, e3});
        getSimpleDao().delete(new SimpleEntityImpl[]{e4, e5});

        Assert.assertTrue(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertTrue(((IHiddenable) getComplexDao().get(e2.getId())).isHidden());
        Assert.assertTrue(((IHiddenable) getComplexDao().get(e3.getId())).isHidden());
        Assert.assertNull(getSimpleDao().get(e4.getId()));
        Assert.assertNull(getSimpleDao().get(e5.getId()));
    }

    /**
     * Test method for {@link com.szczytowski.genericdao.api.IDao#delete(T[])}.
     */
    public void testDeleteArrayException() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, true, false, null);

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2});

        Assert.assertFalse(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertFalse(((IHiddenable) getComplexDao().get(e2.getId())).isHidden());

        try {
            getComplexDao().delete(new ComplexEntityImpl[]{e1, e2});
            fail("Cannot delete default entity");
        } catch (UnsupportedOperationException e) {
        }
    }

    /**
     * Test method for {@link com.szczytowski.genericdao.api.IDao#deleteAll()}.
     */
    public void testDeleteAll() {
        ComplexEntityImpl e1 = getComplexEntity("prop1", false, false, false, null);
        ComplexEntityImpl e2 = getComplexEntity("prop2", true, false, false, null);
        ComplexEntityImpl e3 = getComplexEntity("prop3", true, true, false, e1);
        SimpleEntityImpl e4 = getSimpleEntity("prop3");

        getComplexDao().save(new ComplexEntityImpl[]{e1, e2, e3});
        getSimpleDao().save(e4);

        Assert.assertFalse(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertFalse(((IHiddenable) getComplexDao().get(e2.getId())).isHidden());
        Assert.assertFalse(((IHiddenable) getComplexDao().get(e3.getId())).isHidden());
        Assert.assertNotNull(getSimpleDao().get(e4.getId()));

        getComplexDao().deleteAll();
        getSimpleDao().deleteAll();

        Assert.assertTrue(((IHiddenable) getComplexDao().get(e1.getId())).isHidden());
        Assert.assertTrue(((IHiddenable) getComplexDao().get(e2.getId())).isHidden());
        Assert.assertTrue(((IHiddenable) getComplexDao().get(e3.getId())).isHidden());
        Assert.assertNull(getSimpleDao().get(e4.getId()));
    }

    /**
     * Test method for {@link com.szczytowski.genericdao.api.IDao#isActivable()}.
     */
    public void testIsActivable() {
        Assert.assertTrue(getComplexDao().isActivable());
        Assert.assertFalse(getSimpleDao().isActivable());
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#isDefaultable()}.
     */
    public void testIsDefaultable() {
        Assert.assertTrue(getComplexDao().isDefaultable());
        Assert.assertFalse(getSimpleDao().isDefaultable());
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#isHiddenable()}.
     */
    public void testIsHiddenable() {
        Assert.assertTrue(getComplexDao().isHiddenable());
        Assert.assertFalse(getSimpleDao().isHiddenable());
    }

    /**
     * Test method for
     * {@link com.szczytowski.genericdao.api.IDao#isInheritable()}.
     */
    public void testIsInheritable() {
        Assert.assertTrue(getComplexDao().isInheritable());
        Assert.assertFalse(getSimpleDao().isHiddenable());
    }
}