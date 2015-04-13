Java toolkit which makes DAO manager creating easier. It produced DAO compatible with JPA specification. It has implemented CRUD operations and some features (active, hidden, default, etc.). It also extends standard API for criteria (like Hibernate ones).

# Examples of using #

Simple DAO manager

```
public class SimpleDaoImpl extends GenericDao<EntityImpl, Long> {}
```

Simple DAO manager without creating any class

```
IDao<EntityImpl, Long> dao = new GenericDao<EntityImpl, Long>();
```

Complex DAO manager with user defined methods

```
public class ComplexDaoImpl extends GenericDao<EntityImpl, Long> {

    public List<EntityImpl> getByProp(String prop) {
        return getEntityManager().createQuery("from EntityImpl where prop = ?").setParameter(1, prop).getResultList();
    }

}
```

Complex DAO manager with criteria

```
public class CriteriaDaoImpl extends GenericDao<EntityImpl, Long> {

    public List<EntityImpl> getByProp(String prop) {
        return findByCriteria(Criteria.forClass(EntityImpl.class).add(Restrictions.eq("prop", prop)));
    }


    public EntityImpl getUniqueByProp(String prop) {
        return findUniqueByCriteria(Criteria.forClass(EntityImpl.class).add(Restrictions.eq("prop", prop)).setMaxResults(1));
    }

}
```
