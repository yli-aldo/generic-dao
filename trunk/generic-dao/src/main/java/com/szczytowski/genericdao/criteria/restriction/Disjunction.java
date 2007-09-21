package com.szczytowski.genericdao.criteria.restriction;

/**
 * Disjunction (OR).
 *
 * @author Maciej Szczytowsko <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public class Disjunction extends Junction {

    /**
     * Create new disjunction.
     */
    protected Disjunction() {
        super("or");
    }
}
