/**
 *
 */
package com.szczytowski.genericdao.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import com.szczytowski.genericdao.api.IEntity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * {@link IEntity} implementation used in unit tests.
 */
@Entity
@Table(name = "test_entity2")
public class SimpleEntityImpl implements IEntity<Long> {

    private static final long serialVersionUID = 6304928944916666344L;

    /**
     * Test property.
     */
    public static final String P_PROP = "prop";

    /**
     * Integer property.
     */
    public static final String P_INT = "number";

    /**
     * Entities set.
     */
    public static final String P_Entities = "entities";

    @Id
    @SequenceGenerator(name = "gen_test_entity2_id", sequenceName = "test_entity2_id_seq", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_test_entity2_id")
    private Long id;

    @Column(name = "prop", nullable = true, length = 64)
    private String prop;

    @Column(name = "number", nullable = true)
    private Integer number;

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ComplexEntityImpl> entities;

    /**
     * Constructor.
     */
    public SimpleEntityImpl() {
    }

    /**
     * Constructor.
     * 
     * @param prop test property
     * @param number test number
     */
    public SimpleEntityImpl(String prop, Integer number) {
        this.prop = prop;
        this.number = number;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
     * @return test entities
     */
    public Set<ComplexEntityImpl> getEntities() {
        return entities;
    }

    /**
     * @param entities test entities
     */
    public void setEntities(Set<ComplexEntityImpl> entities) {
        this.entities = entities;
    }

    /**
     * @return test number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param number test number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimpleEntityImpl == false) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        SimpleEntityImpl o = (SimpleEntityImpl) obj;

        return new EqualsBuilder().append(prop, o.getProp()).append(number, o.getNumber()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(prop).append(number).toHashCode();
    }
}