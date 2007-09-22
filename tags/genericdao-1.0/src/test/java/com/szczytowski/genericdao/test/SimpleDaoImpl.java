package com.szczytowski.genericdao.test;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.impl.GenericDao;
import java.util.List;
import javax.persistence.Query;

/**
 * {@link IDao} implementation used in unit tests.
 */
public class SimpleDaoImpl extends GenericDao<SimpleEntityImpl, Long> {

    /**
     * Find by criteria.
     *
     * @param criteria criteria
     * @return results
     */
    public List getByCriteria(Criteria criteria) {
        return findByCriteria(criteria);
    }

    /**
     * Find unique by criteria.
     *
     * @param criteria criteria
     * @return result
     */
    public Object getUniqueByCriteria(Criteria criteria) {
        return findUniqueByCriteria(criteria);
    }
    
    /**
     * Find by query.
     *
     * @param eql eql
     * @return results
     */
    public List getByQuery(String eql, Object... params) {
        Query query = getEntityManager().createQuery(eql);
        
        for(int i = 0; i < params.length; i++) {        
            query.setParameter(i+1, params[i]);
        }
        
        return query.getResultList();
    }

    /**
     * Find unique by query.
     *
     * @param eql eql
     * @return result
     */
    public Object getUniqueByQuery(String eql, Object... params) {
        Query query = getEntityManager().createQuery(eql);
        
        for(int i = 0; i < params.length; i++) {        
            query.setParameter(i+1, params[i]);
        }
        
        return query.getSingleResult();
    }
}
