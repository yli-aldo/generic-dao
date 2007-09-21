package com.szczytowski.genericdao.criteria;

class OrderEntry {

        private final Order order;

        private final Criteria criteria;

        OrderEntry(Order order, Criteria criteria) {
            this.criteria = criteria;
            this.order = order;
        }

        Order getOrder() {
            return order;
        }

        Criteria getCriteria() {
            return criteria;
        }
    }
