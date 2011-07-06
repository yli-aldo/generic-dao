package com.szczytowski.genericdao.test;

import com.szczytowski.genericdao.api.IDao;
import org.springframework.test.jpa.AbstractJpaTests;
import com.szczytowski.genericdao.api.IEntity;

/**
 * Abstract unit tests for {@link IDao} class.
 */
public abstract class AbstractTest extends AbstractJpaTests {

    private IDao complexDao;

    private IDao simpleDao;

    /**
     * Constructor.
     */
    public AbstractTest() {
        setAutowireMode(AUTOWIRE_BY_NAME);
    }

    /**
     * Setter for complex dao.
     *
     * @param complexDao complex dao
     * @see #getComplexEntity(String, boolean, boolean, boolean, IEntity)
     */
    public void setComplexDao(IDao complexDao) {
        this.complexDao = complexDao;
    }

    /**
     * @return complex dao.
     */
    protected ComplexDaoImpl getComplexDao() {
        return (ComplexDaoImpl)complexDao;
    }   
    
    /**
     * Setter for simple dao.
     *
     * @param simpleDao simple dao
     * @see #getSimpleEntity(String)
     */
    public void setSimpleDao(IDao simpleDao) {
        this.simpleDao = simpleDao;
    }

    /**
     * @return simple dao
     */
    protected SimpleDaoImpl getSimpleDao() {
        return (SimpleDaoImpl)simpleDao;
    }

    /**
     * Entity which implements {@link IEntity}, {@link IHiddenable},
     * {@link IInheritable}, {@link IActivable} and {@link IDefaultable}.
     *
     * @param prop example property
     * @param isActive is entity active
     * @param isDefault is entity default
     * @param isHidden is entity hidden
     * @param parent entity's parent
     * @return new entity
     */
    protected ComplexEntityImpl getComplexEntity(String prop, boolean isActive, boolean isDefault, boolean isHidden, IEntity parent) {
        return new ComplexEntityImpl(prop, isActive, isDefault, isHidden, (ComplexEntityImpl) parent, null);
    }

    /**
     * Entity which implements {@link IEntity}, {@link IHiddenable},
     * {@link IInheritable}, {@link IActivable} and {@link IDefaultable}.
     *
     * @param prop example property
     * @param isActive is entity active
     * @param isDefault is entity default
     * @param isHidden is entity hidden
     * @param parent entity's parent
     * @param entity test entity
     * @return new entity
     */
    protected ComplexEntityImpl getComplexEntity(String prop, boolean isActive, boolean isDefault, boolean isHidden, IEntity parent, IEntity entity) {
        return new ComplexEntityImpl(prop, isActive, isDefault, isHidden, (ComplexEntityImpl) parent, (SimpleEntityImpl) entity);
    }

    /**
     * Entity which implements only {@link IEntity}.
     *
     * @param prop example property
     * @return new entity
     */
    protected SimpleEntityImpl getSimpleEntity(String prop) {
        return new SimpleEntityImpl(prop, null);
    }

    /**
     * Entity which implements only {@link IEntity}.
     *
     * @param prop example property
     * @param number test number
     * @return new entity
     */
    protected SimpleEntityImpl getSimpleEntity(String prop, Integer number) {
        return new SimpleEntityImpl(prop, number);
    }

    /**
     * Get spring configurations.
     *
     * @return spring configurations
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:/com/szczytowski/genericdao/test/context.xml"};
    }
}