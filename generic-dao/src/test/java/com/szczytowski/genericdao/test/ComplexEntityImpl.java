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
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.szczytowski.genericdao.api.IActivable;
import com.szczytowski.genericdao.api.IDefaultable;
import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.api.IHiddenable;
import com.szczytowski.genericdao.api.IInheritable;

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

	@Id
	@SequenceGenerator(name = "gen_test_entity1_id", sequenceName = "test_entity1_id_seq", allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_test_entity1_id")
	private Long id;

	@Column(name = "prop", nullable = false, length = 64)
	private String prop;

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
	 */
	public ComplexEntityImpl(String prop, boolean isActive, boolean isDefault, boolean isHidden, ComplexEntityImpl parent) {
		this.prop = prop;
		this.isActive = isActive;
		this.isDefault = isDefault;
		this.isHidden = isHidden;
		this.parent = parent;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public IEntity getExample() {
		ComplexEntityImpl example = new ComplexEntityImpl();
		example.setParent(parent);
		return example;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public ComplexEntityImpl getParent() {
		return parent;
	}

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
	 * @param prop
	 *            test property
	 */
	public void setProp(String prop) {
		this.prop = prop;
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

		return new EqualsBuilder().append(parent, o.getParent()).append(prop, o.getProp()).append(isActive, o.isActive()).append(isHidden, o.isHidden())
				.append(isDefault, o.isDefault()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(parent).append(prop).append(isActive).append(isDefault).append(isHidden).toHashCode();
	}

}