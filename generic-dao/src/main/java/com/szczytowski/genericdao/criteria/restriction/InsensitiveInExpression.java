package com.szczytowski.genericdao.criteria.restriction;

import com.szczytowski.genericdao.criteria.Criteria;
import com.szczytowski.genericdao.criteria.Criterion;

/**
 * Insensitive In expression.
 * 
 * @author Simon Lavigne-Giroux <simon.lg@gmail.com>
 * @since 1.3
 */
public class InsensitiveInExpression implements Criterion {

  private final String property;
  private final String[] values;

  /**
   * Create new insensitive in expression.
   * 
   * @param property property
   * @param values values
   */
  protected InsensitiveInExpression(String property, String[] values) {
    this.property = property;
    this.values = values;
  }

  @Override
  public String toSqlString(Criteria criteria, Criteria.CriteriaQuery criteriaQuery) {
    String sql = "lower(" + criteriaQuery.getPropertyName(property, criteria) + ") in (";

    for (String v : values) {
      criteriaQuery.setParam(v.toLowerCase());
      sql += "?,";
    }

    return sql.substring(0, sql.length() - 1) + ")";
  }
}
