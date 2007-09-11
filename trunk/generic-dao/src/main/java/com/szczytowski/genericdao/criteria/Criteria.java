package com.szczytowski.genericdao.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.szczytowski.genericdao.api.IEntity;
import com.szczytowski.genericdao.exception.GenericDaoException;

public class Criteria {

	private String name;
	
	private Criteria parent;

	private Projection projection;

	private Criteria projectionCriteria;

	private List<CriterionEntry> criterionList = new ArrayList<CriterionEntry>();

	private List<OrderEntry> orderList = new ArrayList<OrderEntry>();

	private List<Subcriteria> subcriteriaList = new ArrayList<Subcriteria>();

	private Integer maxResults;

	private Integer firstResult;
	
	/**
	 * Create new criteria for specified <code>IEntity</code> implementation.
	 * @see IEntity
	 */
	public static Criteria forClass(Class<? extends IEntity> entity) {
		return new Criteria(getEntityName(entity));
	}

	private Criteria(String name) {
		this(name, null);
	}
	
	private Criteria(String name, Criteria parent) {
		this.name = name;
		this.parent = parent;
	}
	
	/**
	 * Get parent criteria. If this is not an instance of <code>Subcriteria</code> method will return null.
	 */
	public Criteria getParent() {
		return parent;
	}
	
	/**
	 * Get criteria entity name. If this is an instance of <code>Criteria</code> (the root one) method will return null.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Used to specify that the query results will be a projection (scalar in
	 * nature). The individual components contained within the given
	 * {@link Projection projection} determines the overall "shape" of the query
	 * result.
	 */
	public Criteria setProjection(Projection projection) {
		this.projection = projection;
		this.projectionCriteria = this;
		return this;
	}

	/**
	 * Add a {@link Criterion restriction} to constrain the results to be
	 * retrieved.
	 */
	public Criteria add(Criterion criterion) {
		criterionList.add(new CriterionEntry(criterion, this));
		return this;
	}

	/**
	 * Add an {@link Order ordering} to the result set.
	 */
	public Criteria addOrder(Order order) {
		orderList.add(new OrderEntry(order, this));
		return this;
	}

	/**
	 * Create a new <tt>Criteria</tt>, "rooted" at the associated entity.
	 */
	public Criteria createCriteria(String name) throws GenericDaoException {
		return new Subcriteria(this, name);
	}

	/**
	 * Set a limit upon the number of objects to be retrieved.
	 */
	public Criteria setMaxResults(int maxResults) {
		this.maxResults = new Integer(maxResults);
		return this;
	}

	/**
	 * Set the first result to be retrieved.
	 */
	public Criteria setFirstResult(int firstResult) {
		this.firstResult = new Integer(firstResult);
		return this;
	}

	private Query prepareQuery(EntityManager entityManager)
			throws GenericDaoException {

		CriteriaQuery criteriaQuery = new CriteriaQuery();

		String sql = "";

		if (projection != null) {
			String projectionSql = projection.toSqlString(projectionCriteria,
					1, criteriaQuery);
			if (projectionSql.length() > 0) {
				sql += "select " + projectionSql + " ";
			}
		}

		sql += "from " + name + " ";

		String criterionSql = "";

		for (CriterionEntry criterion : criterionList) {
			if (criterionSql.length() > 0) {
				criterionSql += " and ";
			}
			criterionSql += criterion.getCriterion().toSqlString(
					criterion.getCriteria(), criteriaQuery);
		}

		if (criterionSql.length() > 0) {
			sql += "where " + criterionSql + " ";
		}

		if (projection != null) {
			String groupBySql = projection.toGroupSqlString(projectionCriteria,
					criteriaQuery);
			if (groupBySql.length() > 0) {
				sql += "group by " + groupBySql + " ";
			}
		}

		String orderSql = "";

		for (OrderEntry order : orderList) {
			if (orderSql.length() > 0) {
				orderSql += ",";
			}
			orderSql += order.getOrder().toSqlString(order.getCriteria(),
					criteriaQuery);
		}

		if (orderSql.length() > 0) {
			sql += "order by " + orderSql + " ";
		}

		if (firstResult != null) {
			sql += "offset " + firstResult + " ";
		}

		if (maxResults != null) {
			sql += "limit " + maxResults + " ";
		}

		System.out.println("### " + sql + " ###");

		Query query = entityManager.createQuery(sql);

		int i = 1;

		for (Object property : criteriaQuery.getProperties()) {
			query.setParameter(i++, property);
		}

		return query;
	}

	/**
	 * Get the results.
	 */
	public List list(EntityManager entityManager) throws GenericDaoException {
		return prepareQuery(entityManager).getResultList();
	}

	/**
	 * Convenience method to return a single instance that matches the query, or
	 * null if the query returns no results.
	 */
	public Object uniqueResult(EntityManager entityManager)
			throws GenericDaoException {
		return prepareQuery(entityManager).getSingleResult();
	}

	protected static String getEntityName(Class<? extends IEntity> type) {
		Entity entity = type.getAnnotation(Entity.class);

		if (entity == null || entity.name() == null
				|| entity.name().length() == 0) {
			return type.getSimpleName();
		} else {
			return entity.name();
		}
	}

	// Inner classes ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public final class Subcriteria extends Criteria {

		private Subcriteria(Criteria parent, String name) {
			super(name, parent);
			Criteria.this.subcriteriaList.add(this);
		}

		public Criteria add(Criterion criterion) {
			Criteria.this.criterionList
					.add(new CriterionEntry(criterion, this));
			return this;
		}

		public Criteria addOrder(Order order) {
			Criteria.this.orderList.add(new OrderEntry(order, this));
			return this;
		}

		public Criteria createCriteria(String name) throws GenericDaoException {
			return new Subcriteria(Subcriteria.this, name);
		}

		public List list(EntityManager entityManager)
				throws GenericDaoException {
			return Criteria.this.list(entityManager);
		}

		public Object uniqueResult(EntityManager entityManager)
				throws GenericDaoException {
			return Criteria.this.uniqueResult(entityManager);
		}

		public Criteria setFirstResult(int firstResult) {
			Criteria.this.setFirstResult(firstResult);
			return this;
		}

		public Criteria setMaxResults(int maxResults) {
			Criteria.this.setMaxResults(maxResults);
			return this;
		}

		public Criteria setProjection(Projection projection) {
			Criteria.this.projection = projection;
			Criteria.this.projectionCriteria = this;
			return this;
		}
	}

	public static interface Projection {

		public String toSqlString(Criteria criteria, int position,
				CriteriaQuery criteriaQuery) throws GenericDaoException;

		public String toGroupSqlString(Criteria criteria,
				CriteriaQuery criteriaQuery) throws GenericDaoException;

		public boolean isGrouped();

	}

	public static interface Criterion {

		public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
				throws GenericDaoException;

	}

	public static final class CriterionEntry {
		private final Criterion criterion;

		private final Criteria criteria;

		private CriterionEntry(Criterion criterion, Criteria criteria) {
			this.criteria = criteria;
			this.criterion = criterion;
		}

		public Criterion getCriterion() {
			return criterion;
		}

		public Criteria getCriteria() {
			return criteria;
		}
	}

	public static final class OrderEntry {
		private final Order order;

		private final Criteria criteria;

		private OrderEntry(Order order, Criteria criteria) {
			this.criteria = criteria;
			this.order = order;
		}

		public Order getOrder() {
			return order;
		}

		public Criteria getCriteria() {
			return criteria;
		}
	}
}
