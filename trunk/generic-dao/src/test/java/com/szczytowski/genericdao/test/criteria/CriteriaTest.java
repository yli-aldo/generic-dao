package com.szczytowski.genericdao.test.criteria;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import com.szczytowski.genericdao.api.IDao;
import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Criterion;
import com.szczytowski.genericdao.criteria.Order;
import com.szczytowski.genericdao.criteria.Projection;
import com.szczytowski.genericdao.criteria.projection.Projections;
import com.szczytowski.genericdao.criteria.restriction.Restrictions;
import com.szczytowski.genericdao.test.AbstractTest;
import com.szczytowski.genericdao.test.ComplexEntityImpl;
import com.szczytowski.genericdao.test.SimpleEntityImpl;

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
    
    public void testRestrictions() {
        SimpleEntityImpl e1 = getSimpleEntity("abc", 1);
        SimpleEntityImpl e2 = getSimpleEntity("deF", 2);
        SimpleEntityImpl e3 = getSimpleEntity("ghi", 3);
        
        getSimpleDao().save(e1);
        getSimpleDao().save(e2);
        getSimpleDao().save(e3);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.idEq(e1.getId()), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.idEq(e2.getId()), e2);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.eq(SimpleEntityImpl.P_INT, 2), e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.ge(SimpleEntityImpl.P_INT, 2), e2, e3);
        testRestriction(SimpleEntityImpl.class, Restrictions.gt(SimpleEntityImpl.P_INT, 2), e3);
        testRestriction(SimpleEntityImpl.class, Restrictions.le(SimpleEntityImpl.P_INT, 2), e1, e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.lt(SimpleEntityImpl.P_INT, 2), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.ne(SimpleEntityImpl.P_INT, 2), e1, e3);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.between(SimpleEntityImpl.P_INT, 1, 3), e1, e2, e3);
        testRestriction(SimpleEntityImpl.class, Restrictions.between(SimpleEntityImpl.P_INT, 2, 3), e2, e3);
        testRestriction(SimpleEntityImpl.class, Restrictions.between(SimpleEntityImpl.P_INT, 1, 2), e1, e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.between(SimpleEntityImpl.P_INT, 2, 2), e2);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.ilike(SimpleEntityImpl.P_PROP, "abc"), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.ilike(SimpleEntityImpl.P_PROP, "AbC"), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.ilike(SimpleEntityImpl.P_PROP, "aBc"), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.ilike(SimpleEntityImpl.P_PROP, "%B%"), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.like(SimpleEntityImpl.P_PROP, "abc"), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.like(SimpleEntityImpl.P_PROP, "abC"));
        testRestriction(SimpleEntityImpl.class, Restrictions.like(SimpleEntityImpl.P_PROP, "ab%"), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.like(SimpleEntityImpl.P_PROP, "a%"), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.like(SimpleEntityImpl.P_PROP, "ab_"), e1);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.in(SimpleEntityImpl.P_INT, new Integer[] { 1, 2 }), e1, e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.in(SimpleEntityImpl.P_INT, Arrays.asList(new Integer[] { 1, 2 })), e1, e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.in(SimpleEntityImpl.P_INT, new Integer[] { 0, 1, 2, 4 }), e1, e2);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.iin(SimpleEntityImpl.P_PROP, new String[] { "ABC", "DEf" }), e1, e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.iin(SimpleEntityImpl.P_PROP, Arrays.asList(new  String[] { "ABC", "DEf" })), e1, e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.iin(SimpleEntityImpl.P_PROP, new String[] { "SOME", "ABC", "DEf" }), e1, e2);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.or(Restrictions.eq(SimpleEntityImpl.P_INT, 1), Restrictions.eq(SimpleEntityImpl.P_INT, 2)), e1, e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.or(Restrictions.eq(SimpleEntityImpl.P_INT, 1), Restrictions.eq(SimpleEntityImpl.P_PROP, "deF")), e1, e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.and(Restrictions.eq(SimpleEntityImpl.P_INT, 1), Restrictions.eq(SimpleEntityImpl.P_INT, 2)));
        testRestriction(SimpleEntityImpl.class, Restrictions.and(Restrictions.eq(SimpleEntityImpl.P_INT, 1), Restrictions.eq(SimpleEntityImpl.P_PROP, "abc")), e1);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(SimpleEntityImpl.P_INT, 1);
        map.put(SimpleEntityImpl.P_PROP, "abc");
        
        testRestriction(SimpleEntityImpl.class, Restrictions.allEq(map), e1);
        
        map.put(SimpleEntityImpl.P_PROP, "def");
        
        testRestriction(SimpleEntityImpl.class, Restrictions.allEq(map));

        testRestriction(SimpleEntityImpl.class, Restrictions.not(Restrictions.eq(SimpleEntityImpl.P_INT, 2)), e1, e3);
        
        SimpleEntityImpl e4 = getSimpleEntity(null, null);
        
        getSimpleDao().save(e4);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.isNull(SimpleEntityImpl.P_INT), e4);
        testRestriction(SimpleEntityImpl.class, Restrictions.isNull(SimpleEntityImpl.P_PROP), e4);
        testRestriction(SimpleEntityImpl.class, Restrictions.isNotNull(SimpleEntityImpl.P_INT), e1, e2, e3);
        testRestriction(SimpleEntityImpl.class, Restrictions.isNotNull(SimpleEntityImpl.P_PROP), e1, e2, e3);
                
        ComplexEntityImpl e5 = getComplexEntity("abc", true, true, false, null, e1);
        ComplexEntityImpl e6 = getComplexEntity("abb", true, true, false, null, e1);
        ComplexEntityImpl e7 = getComplexEntity("abd", true, true, false, null, e1);
        ComplexEntityImpl e8 = getComplexEntity("foo", true, true, false, null, e2);
        ComplexEntityImpl e9 = getComplexEntity("goo", true, true, false, null, e2);
        ComplexEntityImpl e10 = getComplexEntity("hoo", true, true, false, null, e4);
        
        getComplexDao().save(e5);
        getComplexDao().save(e6);
        getComplexDao().save(e7);
        getComplexDao().save(e8);
        getComplexDao().save(e9);
        getComplexDao().save(e10);
        
        testRestriction(ComplexEntityImpl.class, Restrictions.eqProperty(ComplexEntityImpl.P_PROP, ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_PROP), e5);
        testRestriction(ComplexEntityImpl.class, Restrictions.geProperty(ComplexEntityImpl.P_PROP, ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_PROP), e5, e7, e8, e9);
        testRestriction(ComplexEntityImpl.class, Restrictions.gtProperty(ComplexEntityImpl.P_PROP, ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_PROP), e7, e8, e9);
        testRestriction(ComplexEntityImpl.class, Restrictions.leProperty(ComplexEntityImpl.P_PROP, ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_PROP), e5, e6);
        testRestriction(ComplexEntityImpl.class, Restrictions.ltProperty(ComplexEntityImpl.P_PROP, ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_PROP), e6);
        testRestriction(ComplexEntityImpl.class, Restrictions.neProperty(ComplexEntityImpl.P_PROP, ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_PROP), e6, e7, e8, e9);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.isEmpty(SimpleEntityImpl.P_ENTITIES), e3);
        testRestriction(SimpleEntityImpl.class, Restrictions.isNotEmpty(SimpleEntityImpl.P_ENTITIES), e1, e2, e4);
        
        testRestriction(SimpleEntityImpl.class, Restrictions.sizeEq(SimpleEntityImpl.P_ENTITIES, 2L), e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.sizeGe(SimpleEntityImpl.P_ENTITIES, 2L), e1, e2);
        testRestriction(SimpleEntityImpl.class, Restrictions.sizeGt(SimpleEntityImpl.P_ENTITIES, 2L), e1);
        testRestriction(SimpleEntityImpl.class, Restrictions.sizeLe(SimpleEntityImpl.P_ENTITIES, 2L), e2, e3, e4);
        testRestriction(SimpleEntityImpl.class, Restrictions.sizeNe(SimpleEntityImpl.P_ENTITIES, 2L), e1, e3, e4);
                      
        testRestriction(ComplexEntityImpl.class, Restrictions.conjunction().add(Restrictions.eq(ComplexEntityImpl.P_PROP, "abb")).add(Restrictions.eq(ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_PROP, "abc")).add(Restrictions.eq(ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_INT, 1)), e6);
        testRestriction(ComplexEntityImpl.class, Restrictions.disjunction().add(Restrictions.eq(ComplexEntityImpl.P_PROP, "abb")).add(Restrictions.eq(ComplexEntityImpl.P_PROP, "abc")).add(Restrictions.eq(ComplexEntityImpl.P_PROP, "abd")), e5, e6, e7);
                
        testRestriction(ComplexEntityImpl.class, Restrictions.conjunction().add(Restrictions.disjunction().add(Restrictions.eq(ComplexEntityImpl.P_PROP, "abb")).add(Restrictions.eq(ComplexEntityImpl.P_PROP, "abc")).add(Restrictions.eq(ComplexEntityImpl.P_PROP, "abd"))).add(Restrictions.eq(ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_PROP, "abc")).add(Restrictions.eq(ComplexEntityImpl.P_ENTITY + "." + SimpleEntityImpl.P_INT, 1)), e5, e6, e7);
    }

    public void testAlias() {
        SimpleEntityImpl e1 = getSimpleEntity("abc", 1);
        
        getSimpleDao().save(e1);

        ComplexEntityImpl e2 = getComplexEntity("def", true, true, false, null, e1);
        
        getComplexDao().save(e2);

        List<IEntity> results = getComplexDao().getByCriteria(Criteria.forClass(ComplexEntityImpl.class).createAlias(ComplexEntityImpl.P_ENTITY, "aentity").add(Restrictions.eq("aentity." + SimpleEntityImpl.P_PROP, "abc")));

        Assert.assertEquals(1L, results.size());
        Assert.assertEquals(e2.getId(), results.get(0).getId());

        results = getComplexDao().getByCriteria(Criteria.forClass(ComplexEntityImpl.class).createAlias(ComplexEntityImpl.P_ENTITY, "aentity", Criteria.JoinType.LEFT_JOIN).add(Restrictions.eq("aentity." + SimpleEntityImpl.P_PROP, "abc")));

        Assert.assertEquals(1L, results.size());
        Assert.assertEquals(e2.getId(), results.get(0).getId());

        Criteria criteria = Criteria.forClass(ComplexEntityImpl.class);
        criteria.createCriteria(ComplexEntityImpl.P_ENTITY).add(Restrictions.eq(SimpleEntityImpl.P_PROP, "abc"));

        results = getComplexDao().getByCriteria(criteria);

        Assert.assertEquals(1L, results.size());
        Assert.assertEquals(e2.getId(), results.get(0).getId());

        criteria = Criteria.forClass(ComplexEntityImpl.class);
        criteria.createCriteria(ComplexEntityImpl.P_ENTITY, Criteria.JoinType.LEFT_JOIN).add(Restrictions.eq(SimpleEntityImpl.P_PROP, "abc"));

        results = getComplexDao().getByCriteria(criteria);

        Assert.assertEquals(1L, results.size());
        Assert.assertEquals(e2.getId(), results.get(0).getId());

        ComplexEntityImpl e3 = getComplexEntity("ghi", true, true, false, e2, e1);
        
        getComplexDao().save(e3);

        results = getComplexDao().getByCriteria(Criteria.forClass(ComplexEntityImpl.class).createAlias(ComplexEntityImpl.P_ENTITY, "aentity").createAlias(ComplexEntityImpl.P_PARENT, "aparent").add(Restrictions.eq("aentity." + SimpleEntityImpl.P_PROP, "abc")).add(Restrictions.eq("aparent." + ComplexEntityImpl.P_PROP, "def")));

        Assert.assertEquals(1L, results.size());
        Assert.assertEquals(e3.getId(), results.get(0).getId());

        criteria = Criteria.forClass(ComplexEntityImpl.class);
        criteria.createCriteria(ComplexEntityImpl.P_ENTITY).add(Restrictions.eq(SimpleEntityImpl.P_PROP, "abc"));
        criteria.createCriteria(ComplexEntityImpl.P_PARENT).add(Restrictions.eq(ComplexEntityImpl.P_PROP, "def"));

        results = getComplexDao().getByCriteria(criteria);

        Assert.assertEquals(1L, results.size());
        Assert.assertEquals(e3.getId(), results.get(0).getId());
    }
    
    private void testRestriction(Class entityClass, Criterion criterion, IEntity... expectedEntities) {
        List<IEntity> results = null;
        
        if(entityClass == SimpleEntityImpl.class) {
            results = getSimpleDao().getByCriteria(Criteria.forClass(SimpleEntityImpl.class).add(criterion).addOrder(Order.asc(IEntity.P_ID)));
        } else {
            results = getSimpleDao().getByCriteria(Criteria.forClass(ComplexEntityImpl.class).add(criterion).addOrder(Order.asc(IEntity.P_ID)));
        }
        
        Assert.assertEquals(expectedEntities.length, results.size());
        
        for(int i = 0; i < expectedEntities.length; i++) {       
            Assert.assertEquals(expectedEntities[i].getId(), results.get(i).getId());           
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
}