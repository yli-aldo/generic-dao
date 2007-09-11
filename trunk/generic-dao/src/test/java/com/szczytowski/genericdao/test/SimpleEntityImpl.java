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

	@Id
	@SequenceGenerator(name = "gen_test_entity2_id", sequenceName = "test_entity2_id_seq", allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_test_entity2_id")
	private Long id;

	@Column(name = "prop", nullable = false, length = 64)
	private String prop;

	/**
	 * Constructor.
	 */
	public SimpleEntityImpl() {
	}

	/**
	 * Constructor.
	 */
	public SimpleEntityImpl(String prop) {
		this.prop = prop;
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimpleEntityImpl == false) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		SimpleEntityImpl o = (SimpleEntityImpl) obj;

		return new EqualsBuilder().append(prop, o.getProp()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(prop).toHashCode();
	}

}