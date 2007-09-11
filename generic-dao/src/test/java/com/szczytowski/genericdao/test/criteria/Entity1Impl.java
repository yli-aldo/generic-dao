/**
 * 
 */
package com.szczytowski.genericdao.test.criteria;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Entity1Impl implements IEntity<Long> {

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
	@SequenceGenerator(name = "gen_test_criteria1_id", sequenceName = "test_criteria1_id_seq", allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_test_criteria1_id")
	private Long id;

	@Column(name = "prop", nullable = true)
	private String prop;
	
	@Column(name = "number", nullable = true)
	private Integer number;
	
	@OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Entity2Impl> entities;

	/**
	 * Constructor.
	 */
	public Entity1Impl() {
	}

	/**
	 * Constructor.
	 */
	public Entity1Impl(String prop, Integer number) {
		this.prop = prop;
		this.number = number;
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


	public Set<Entity2Impl> getEntities() {
		return entities;
	}

	public void setEntities(Set<Entity2Impl> entities) {
		this.entities = entities;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Entity1Impl == false) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		Entity1Impl o = (Entity1Impl) obj;

		return new EqualsBuilder().append(prop, o.getProp()).append(number, o.getNumber()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(prop).append(number).toHashCode();
	}

}