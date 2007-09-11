/**
 * 
 */
package com.szczytowski.genericdao.test.criteria;

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

/**
 * {@link IEntity} implementation used in unit tests.
 */
@Entity
@Table(name = "test_criteria_1")
public class Entity2Impl implements IEntity<Long> {

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
	 * Entity property.
	 */
	public static final String P_ENTITY = "entity";

	@Id
	@SequenceGenerator(name = "gen_test_criteria1_id", sequenceName = "test_criteria1_id_seq", allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_test_criteria1_id")
	private Long id;

	@Column(name = "prop", nullable = true)
	private String prop;
	
	@Column(name = "number", nullable = true)
	private Integer number;
	
	@Column(name = "entity", nullable = true)
	private Entity1Impl entity;

	/**
	 * Constructor.
	 */
	public Entity2Impl() {
	}

	/**
	 * Constructor.
	 */
	public Entity2Impl(String prop, Integer number, Entity1Impl entity) {
		this.prop = prop;
		this.number = number;
		this.entity = entity;
	}

	public Long getId() {
		return id;
	}

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
	 * @param prop
	 *            test property
	 */
	public void setProp(String prop) {
		this.prop = prop;
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Entity1Impl getEntity() {
		return entity;
	}

	public void setEntity(Entity1Impl entity) {
		this.entity = entity;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Entity2Impl == false) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		Entity2Impl o = (Entity2Impl) obj;

		return new EqualsBuilder().append(prop, o.getProp()).append(entity, o.getEntity()).append(number, o.getNumber()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(prop).append(number).append(entity).toHashCode();
	}

}