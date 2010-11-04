/**
 *
 */
package com.szczytowski.genericdao.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.szczytowski.genericdao.api.IActivable;
import com.szczytowski.genericdao.api.IDefaultable;
import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.api.IHiddenable;
import com.szczytowski.genericdao.api.IInheritable;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * {@link IEntity} implementation used in unit tests.
 */
@Entity
@Table(name = "test_entity1")
public class ComplexEntityImpl implements IActivable, IDefaultable, IEntity<Long>, IHiddenable, IInheritable<ComplexEntityImpl> {

    private static final long serialVersionUID = 6304928944916666344L;

    /**
     * Test property.
     */
    public static final String P_PROP = "prop";

    /**
     * Entity property.
     */
    public static final String P_ENTITY = "entity";

    @Id
    @SequenceGenerator(name = "gen_test_entity1_id", sequenceName = "test_entity1_id_seq", allocationSize = 64)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_test_entity1_id")
    private Long id;

    @Column(name = "prop", nullable = true, length = 64)
    private String prop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = true)
    private SimpleEntityImpl entity;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "is_hidden")
    private boolean isHidden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private ComplexEntityImpl parent;

    /**
     * Constructor.
     */
    public ComplexEntityImpl() {
    }

    /**
     * Constructor.
     *
     * @param prop test property
     * @param isActive active flag
     * @param isDefault default flag
     * @param isHidden hidden flag
     * @param parent parent
     * @param entity test entity
     */
    public ComplexEntityImpl(String prop, boolean isActive, boolean isDefault, boolean isHidden, ComplexEntityImpl parent, SimpleEntityImpl entity) {
        this.prop = prop;
        this.isActive = isActive;
        this.isDefault = isDefault;
        this.isHidden = isHidden;
        this.parent = parent;
        this.entity = entity;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public IEntity getExample() {
        ComplexEntityImpl example = new ComplexEntityImpl();
        example.setParent(parent);
        return example;
    }

    @Override
    public boolean isDefault() {
        return isDefault;
    }

    @Override
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isHidden() {
        return isHidden;
    }

    @Override
    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public ComplexEntityImpl getParent() {
        return parent;
    }

    @Override
    public void setParent(ComplexEntityImpl parent) {
        this.parent = parent;
    }

    /**
     * @return test property
     */
    public String getProp() {
        return prop;
    }

    /**
     * @param prop test property
     */
    public void setProp(String prop) {
        this.prop = prop;
    }

    /**
     * @return test entity
     */
    public SimpleEntityImpl getEntity() {
        return entity;
    }

    /**
     * @param entity test entity
     */
    public void setEntity(SimpleEntityImpl entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComplexEntityImpl == false) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        ComplexEntityImpl o = (ComplexEntityImpl) obj;

        return new EqualsBuilder().append(parent, o.getParent()).append(entity, o.getEntity()).append(prop, o.getProp()).append(isActive, o.isActive()).append(isHidden, o.isHidden()).append(isDefault, o.isDefault()).isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(prop).append(entity).append(parent).append(isActive).append(isHidden).append(isDefault).toHashCode();
    }
}