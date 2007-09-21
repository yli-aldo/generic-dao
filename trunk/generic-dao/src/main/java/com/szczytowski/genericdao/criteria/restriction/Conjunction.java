package com.szczytowski.genericdao.criteria.restriction;

/**
 * Conjunction (AND).
 *
 * @author Maciej Szczytowsko <mszczytowski-genericdao@gmail.com>
 * @since 1.0
 */
public class Conjunction extends Junction {

    /**
     * Create new conjunction.
     */
    public Conjunction() {
        super("and");
    }
}
