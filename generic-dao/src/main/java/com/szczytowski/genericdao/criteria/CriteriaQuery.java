package com.szczytowski.genericdao.criteria;

import java.util.ArrayList;
import java.util.List;

public class CriteriaQuery {

	private List<Object> properties = new ArrayList<Object>();
	
	public String getPropertyName(String propertyName, Criteria criteria) {
		if(criteria.getParent() == null) {
			return propertyName;
		} else {
			String name = criteria.getName();
			
			if(propertyName.startsWith(name)) {
				return propertyName;
			} else {
				return name + "." + propertyName;
			}
		}
	}
	
	public void setProperty(Object property) {
		properties.add(property);
	}
	
	public List<Object> getProperties() {
		return properties;
	}
	
}
