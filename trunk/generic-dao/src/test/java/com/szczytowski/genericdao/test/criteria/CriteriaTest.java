package com.szczytowski.genericdao.test.criteria;

import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Order;
import com.szczytowski.genericdao.criteria.Projection;
import com.szczytowski.genericdao.criteria.projection.Projections;
import com.szczytowski.genericdao.criteria.restriction.Restrictions;
import com.szczytowski.genericdao.test.AbstractTest;
import com.szczytowski.genericdao.test.ComplexEntityImpl;
import com.szczytowski.genericdao.test.SimpleEntityImpl;
import java.util.List;
import junit.framework.Assert;

/**
 * Abstract unit tests for {@link IDao} class.
 */
public class CriteriaTest extends AbstractTest {

    /**
     * Constructor.
     */
    public CriteriaTest() {
    }
    
    private void testProjection(Projection projection, Class expectedClass, Object expectedValue) {
        Object result = getSimpleDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).setProjection(projection)).get(0);
        
        Assert.assertEquals(expectedClass, result.getClass());        
        
        if(expectedValue != null) {
            Assert.assertEquals(expectedValue, result);
        }
    }
    
    private void testProjection(Projection projection, Class[] expectedClasses, Object[] expectedValues) {
        Object result = getSimpleDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).setProjection(projection)).get(0);
        
        Assert.assertTrue(result instanceof Object[]);
        
        Object[] results = (Object[])result;
        
        Assert.assertEquals(expectedClasses.length, results.length);
        
        for(int i = 0; i < results.length; i++) {       
            Assert.assertEquals(expectedClasses[i], results[i].getClass());
            
            if(expectedValues[i] != null) {
               Assert.assertEquals(expectedValues[i], results[i]);
            }                       
        }        
    }
    
    public void testOrderBy() {
        SimpleEntityImpl e1 = getSimpleEntity("b", 15);
        SimpleEntityImpl e2 = getSimpleEntity("c", 14);
        SimpleEntityImpl e3 = getSimpleEntity("a", 13);
        SimpleEntityImpl e4 = getSimpleEntity(null, null);
        
        getSimpleDao().save(e1);
        getSimpleDao().save(e2);
        getSimpleDao().save(e3);
        getSimpleDao().save(e4);
        
        List<SimpleEntityImpl> result1 = getComplexDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).addOrder(Order.asc(SimpleEntityImpl.P_ID)));
        
        Assert.assertEquals(e1.getId(), result1.get(0).getId());
        Assert.assertEquals(e2.getId(), result1.get(1).getId());
        Assert.assertEquals(e3.getId(), result1.get(2).getId());
        Assert.assertEquals(e4.getId(), result1.get(3).getId());
        
        List<SimpleEntityImpl> result2a = getComplexDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).addOrder(Order.asc(SimpleEntityImpl.P_INT)));
        
        Assert.assertEquals(e4.getId(), result2a.get(0).getId());
        Assert.assertEquals(e3.getId(), result2a.get(1).getId());
        Assert.assertEquals(e2.getId(), result2a.get(2).getId());
        Assert.assertEquals(e1.getId(), result2a.get(3).getId());
        
        List<SimpleEntityImpl> result2d = getComplexDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).addOrder(Order.desc(SimpleEntityImpl.P_INT)));
        
        Assert.assertEquals(e1.getId(), result2d.get(0).getId());
        Assert.assertEquals(e2.getId(), result2d.get(1).getId());
        Assert.assertEquals(e3.getId(), result2d.get(2).getId());
        Assert.assertEquals(e4.getId(), result2d.get(3).getId());
        
        List<SimpleEntityImpl> result3 = getComplexDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).addOrder(Order.asc(SimpleEntityImpl.P_PROP)));
        
        Assert.assertEquals(e4.getId(), result3.get(0).getId());
        Assert.assertEquals(e3.getId(), result3.get(1).getId());
        Assert.assertEquals(e1.getId(), result3.get(2).getId());
        Assert.assertEquals(e2.getId(), result3.get(3).getId());
        
        ComplexEntityImpl e5 = getComplexEntity("e", true, true, false, null, e1);
        ComplexEntityImpl e6 = getComplexEntity("f", true, true, false, null, e2);
        
        getComplexDao().save(e5);
        getComplexDao().save(e6);
        
        List<ComplexEntityImpl> result4 = getComplexDao().getByCriteria(Criteria.forClass(ComplexEntityImpl.class).addOrder(Order.asc(ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_INT)));
        
        Assert.assertEquals(e6.getId(), result4.get(0).getId());
        Assert.assertEquals(e5.getId(), result4.get(1).getId());
        
        List<ComplexEntityImpl> result5 = getComplexDao().getByCriteria(Criteria.forClass(ComplexEntityImpl.class).createAlias(ComplexEntityImpl.P_ENTITY, "aliastest").addOrder(Order.asc("aliastest." + SimpleEntityImpl.P_INT)));
        
        Assert.assertEquals(e6.getId(), result5.get(0).getId());
        Assert.assertEquals(e5.getId(), result5.get(1).getId());
        
        Criteria criteria = Criteria.forClass(ComplexEntityImpl.class);
        criteria.createCriteria(ComplexEntityImpl.P_ENTITY).addOrder(Order.asc(SimpleEntityImpl.P_INT));
        
        List<ComplexEntityImpl> result6 = getComplexDao().getByCriteria(criteria);
        
        Assert.assertEquals(e6.getId(), result6.get(0).getId());
        Assert.assertEquals(e5.getId(), result6.get(1).getId());        
    }
    
    public void testLimitAndOffset() {
        SimpleEntityImpl e1 = getSimpleEntity("a", 1);
        SimpleEntityImpl e2 = getSimpleEntity("b", 2);
        SimpleEntityImpl e3 = getSimpleEntity("c", 3);
        
        getSimpleDao().save(e1);
        getSimpleDao().save(e2);
        getSimpleDao().save(e3);
        
        List<SimpleEntityImpl> result1 = getComplexDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).addOrder(Order.asc(SimpleEntityImpl.P_ID)));
        
        Assert.assertEquals(3, result1.size());
        
        List<SimpleEntityImpl> result2 = getComplexDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).addOrder(Order.asc(SimpleEntityImpl.P_ID)).setFirstResult(1));

        Assert.assertEquals(2, result2.size());
        Assert.assertEquals(e2.getId(), result2.get(0).getId());
        Assert.assertEquals(e3.getId(), result2.get(1).getId());
        
        List<SimpleEntityImpl> result3 = getComplexDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).addOrder(Order.asc(SimpleEntityImpl.P_ID)).setMaxResults(2));
        
        Assert.assertEquals(2, result3.size());
        Assert.assertEquals(e1.getId(), result3.get(0).getId());
        Assert.assertEquals(e2.getId(), result3.get(1).getId());
        
        List<SimpleEntityImpl> result4 = getComplexDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).addOrder(Order.asc(SimpleEntityImpl.P_ID)).setFirstResult(1).setMaxResults(1));
        
        Assert.assertEquals(1, result4.size());
        Assert.assertEquals(e2.getId(), result4.get(0).getId());
        
        List<SimpleEntityImpl> result5 = getComplexDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).addOrder(Order.asc(SimpleEntityImpl.P_ID)).setFirstResult(2).setMaxResults(2));
        
        Assert.assertEquals(1, result5.size());
        Assert.assertEquals(e3.getId(), result5.get(0).getId());
    }
    
    public void testProjection() {  
        SimpleEntityImpl e1 = getSimpleEntity("testProp", 13);
        
        getSimpleDao().save(e1);
        
        testProjection(Projections.id(), Long.class, e1.getId());
        testProjection(Projections.property(SimpleEntityImpl.P_PROP), String.class, "testProp");
        testProjection(Projections.property(SimpleEntityImpl.P_INT), Integer.class, 13);
        testProjection(Projections.groupProperty(SimpleEntityImpl.P_PROP), String.class, "testProp");
        testProjection(Projections.groupProperty(SimpleEntityImpl.P_INT), Integer.class, 13);
        testProjection(Projections.rowCount(), Long.class, 1L);
        testProjection(Projections.sum(SimpleEntityImpl.P_INT), Long.class, 13L);
        testProjection(Projections.avg(SimpleEntityImpl.P_INT), Double.class, 13D);
        testProjection(Projections.count(SimpleEntityImpl.P_INT), Long.class, 1L);
        testProjection(Projections.max(SimpleEntityImpl.P_INT), Integer.class, 13);
        testProjection(Projections.min(SimpleEntityImpl.P_INT), Integer.class, 13);
        testProjection(Projections.countDistinct(SimpleEntityImpl.P_INT), Long.class, 1L);
        testProjection(Projections.eql(SimpleEntityImpl.P_INT), Integer.class, 13);
        testProjection(Projections.eql("'testEql'"), String.class, "testEql");
        testProjection(Projections.groupEql(SimpleEntityImpl.P_INT, SimpleEntityImpl.P_INT), Integer.class, 13);
        testProjection(Projections.groupEql("'testEql'", IEntity.P_ID), String.class, "testEql");
        
        testProjection(Projections.projectionList().add(Projections.id()).add(Projections.property(SimpleEntityImpl.P_INT)), new Class[] {Long.class, Integer.class}, new Object[] {e1.getId(), 13});
        
        SimpleEntityImpl e2 = getSimpleEntity("testFoo", 14);
        SimpleEntityImpl e3 = getSimpleEntity("testBoo", 15);
        
        getSimpleDao().save(e2);
        getSimpleDao().save(e3);
        
        testProjection(Projections.rowCount(), Long.class, 3L);        
        testProjection(Projections.sum(SimpleEntityImpl.P_INT), Long.class, 42L);
        testProjection(Projections.avg(SimpleEntityImpl.P_INT), Double.class, 14D);
        testProjection(Projections.count(SimpleEntityImpl.P_INT), Long.class, 3L);
        testProjection(Projections.max(SimpleEntityImpl.P_INT), Integer.class, 15);
        testProjection(Projections.min(SimpleEntityImpl.P_INT), Integer.class, 13);
        testProjection(Projections.countDistinct(SimpleEntityImpl.P_INT), Long.class, 3L);
        
        SimpleEntityImpl e4 = getSimpleEntity("testFoo", 14);
        SimpleEntityImpl e5 = getSimpleEntity("testBoo", 15);
        
        getSimpleDao().save(e4);
        getSimpleDao().save(e5);
        
        testProjection(Projections.rowCount(), Long.class, 5L);        
        testProjection(Projections.count(SimpleEntityImpl.P_INT), Long.class, 5L);
        testProjection(Projections.countDistinct(SimpleEntityImpl.P_INT), Long.class, 3L);
        testProjection(Projections.count(SimpleEntityImpl.P_PROP), Long.class, 5L);
        testProjection(Projections.countDistinct(SimpleEntityImpl.P_PROP), Long.class, 3L);
        
        SimpleEntityImpl e6 = getSimpleEntity("testCoo", null);
        SimpleEntityImpl e7 = getSimpleEntity(null, 16);
        
        getSimpleDao().save(e6);
        getSimpleDao().save(e7);
        
        testProjection(Projections.rowCount(), Long.class, 7L);        
        testProjection(Projections.count(SimpleEntityImpl.P_INT), Long.class, 6L);
        testProjection(Projections.countDistinct(SimpleEntityImpl.P_INT), Long.class, 4L);
        testProjection(Projections.count(SimpleEntityImpl.P_PROP), Long.class, 6L);
        testProjection(Projections.countDistinct(SimpleEntityImpl.P_PROP), Long.class, 4L);
        
        ComplexEntityImpl e8 = getComplexEntity("testDoo", true, true, false, null, e6);
        ComplexEntityImpl e9 = getComplexEntity("testDoo", true, true, false, e8, e7);
        
        getComplexDao().save(e8);
        getComplexDao().save(e9);
        
        Criteria criteria1 = Criteria.forClass(ComplexEntityImpl.class).add(Restrictions.idEq(e8.getId()));
        criteria1.createCriteria(ComplexEntityImpl.P_ENTITY).setProjection(Projections.id());
        
        Object result1 = getComplexDao().getByCriteria(criteria1).get(0);
        
        Assert.assertEquals(Long.class, result1.getClass());        
        Assert.assertEquals(e6.getId(), result1);
        
        Criteria criteria2 = Criteria.forClass(ComplexEntityImpl.class).add(Restrictions.idEq(e8.getId())).createAlias(ComplexEntityImpl.P_ENTITY, "testalias").setProjection(Projections.property("testalias." + SimpleEntityImpl.P_ID));
        
        Object result2 = getComplexDao().getByCriteria(criteria2).get(0);
        
        Assert.assertEquals(Long.class, result2.getClass());        
        Assert.assertEquals(e6.getId(), result2);
        
        Criteria criteria3 = Criteria.forClass(ComplexEntityImpl.class).add(Restrictions.idEq(e8.getId())).setProjection(Projections.property(ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_ID));
        
        Object result3 = getComplexDao().getByCriteria(criteria3).get(0);
        
        Assert.assertEquals(Long.class, result3.getClass());        
        Assert.assertEquals(e6.getId(), result3);
        
        Criteria criteria4 = Criteria.forClass(ComplexEntityImpl.class).add(Restrictions.idEq(e9.getId()));
        criteria4.createCriteria(ComplexEntityImpl.P_PARENT).setProjection(Projections.id());
        
        Object result4 = getComplexDao().getByCriteria(criteria4).get(0);
        
        Assert.assertEquals(Long.class, result4.getClass());        
        Assert.assertEquals(e8.getId(), result4);      
    }
}