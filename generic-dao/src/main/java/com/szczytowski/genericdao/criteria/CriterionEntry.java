package com.szczytowski.genericdao.criteria;

class CriterionEntry {

    private final Criterion criterion;

    private final Criteria criteria;

    CriterionEntry(Criterion criterion, Criteria criteria) {
        this.criteria = criteria;
        this.criterion = criterion;
    }

    Criterion getCriterion() {
        return criterion;
    }

    Criteria getCriteria() {
        return criteria;
    }
    
}