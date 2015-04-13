This example shows how to use generic-dao with Spring Framework and Hibernate.

Things that you need:
  * JPA configuration (persistance.xml)
  * database configuration (db.properties)
  * application context
  * entity class
  * dao class

JARs that you need:
  * genericdao 1.0
  * persistence-api 1.0
  * spring 2.0.6
  * hibernate 3.2.2.ga
  * hibernate-entitymanager 3.2.1.ga
  * hibernate-annotations 3.2.1.ga
  * hsqldb 1.8.0.7
  * commons-lang 2.3

# JPA configuration #

First, we will create JPA configuration (persistance.xml). In this sample I've used HSQL in-memory database and `your.domain.YourEntityClass` class, which we will create later.

```
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="1.0" xmlns="http://java.sun.com/xml/ns/persistence" 
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence              
             http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd">
    <persistence-unit name="generic-dao" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.ejb.HibernatePersistence</provider>
        <class>your.domain.YourEntityClass</class>
        <properties>
            <property name="hibernate.connection.driver_class" value="org.hsqldb.jdbcDriver"/>
            <property name="hibernate.connection.url" value="jdbc:hsqldb:mem:genericdao"/>
            <property name="hibernate.connection.username" value="sa"/>
            <property name="hibernate.connection.password" value=""/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.HSQLDialect"/>      
            <property name="hibernate.cache.provider_class" value="org.hibernate.cache.HashtableCacheProvider"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="false"/>
            <property name="hibernate.use_sql_comments" value="false"/>
            <property name="hibernate.query.substitutions" value="true 1, false 0"/>
        </properties>
    </persistence-unit>
</persistence>
```

# Database configuration #

Next, we will make `db.properties` file, which contains database related configuration.

```
db.driver=org.hsqldb.jdbcDriver
db.url=jdbc:hsqldb:mem:genericdao
db.username=sa
db.password=
```

# Application context #

In third step in file `applicationContext.xml` we will create Spring application configuration.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd">
```

Reading properties from `db.properties`.

```
    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="location">
            <value>classpath:db.properties</value>
        </property>
    </bean>
```

Creating data source according to information from `db.properties`.

```
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="${db.driver}"/>
        <property name="url" value="${db.url}"/>
        <property name="username" value="${db.username}"/>
        <property name="password" value="${db.password}"/>
    </bean>
```

Creating entity manager factory using ours `persistence.xml` and data source.

```
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="persistenceXmlLocation" value="classpath:persistence.xml" />
        <property name="persistenceUnitName" value="generic-dao" />		
        <property name="dataSource" ref="dataSource"/>
    </bean>
```

Creating annotated transaction manager. Read more about `@Transaction` annotation in [Spring Reference](http://www.springframework.org/docs/reference/transaction.html).

```
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
        <property name="dataSource" ref="dataSource"/>
    </bean>
    
    <tx:annotation-driven transaction-manager="transactionManager" />
```

Creating annotation for injection EntityManagerFactory and EntityManager.

```
    <bean class="org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor" />
```

Creating ours dao.

```
    <bean id="dao" class="your.domain.YourDaoClass" />
```

And that's all :)

```
</beans>
```


# Entity class #

Now we will create entity class.

```
@Entity
@Table(name = "your_entity")
public class YourEntityClass implements IEntity<Long> {
 
    @Id
    @SequenceGenerator(name = "gen_test_entity2_id", sequenceName = "test_entity2_id_seq", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_test_entity2_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    /*
     * Create constructors, getters, setters, isEquals, hashcode, etc.
     */
}
```

# Dao #

In last step we will create dao.

```
public interface YourDaoInterface extends IDao<YourEntityClass, Long> {

    YourEntityClass getByName(String name);
}
```


```
public class YourDaoClass extends GenericDao<YourEntityClass, Long> implements YourDaoInterface {

    public YourEntityClass getByName(String name) {
        List<YourEntityClass> list = findByCriteria(Criteria.forClass(YourEntityClass.class).add(Restrictions.eq("name", name)));		

        if(list == null || list.size() != 1) {
            return null;
        }

        return list.get(0);
    }
}
```





