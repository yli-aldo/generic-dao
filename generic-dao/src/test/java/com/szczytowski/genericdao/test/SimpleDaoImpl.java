/**
 * 
 */
package com.szczytowski.genericdao.test;

import com.szczytowski.genericdao.api.IDao;
import com.szczytowski.genericdao.impl.GenericDao;

/**
 * {@link IDao} implementation used in unit tests.
 */
public class SimpleDaoImpl extends GenericDao<SimpleEntityImpl, Long> {

}
