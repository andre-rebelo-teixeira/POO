package population;

import java.util.Comparator;

/**
 * Comparator for comparing two Individual objects based on their comfort levels.
 * This comparator sorts individuals in descending order of comfort level.
 */
class IndividualComparator implements Comparator<Individual> {

    /**
     * Compares two Individual objects based on their comfort levels.
     * The comparison is done in descending order.
     *
     * @param o1 The first Individual object to be compared.
     * @param o2 The second Individual object to be compared.
     * @return A negative integer, zero, or a positive integer as the first argument
     *         has a greater, equal, or lesser comfort level than the second.
     */
    @Override
    public int compare(Individual o1, Individual o2) {
        return -Float.compare(o1.get_comfort_level(), o2.get_comfort_level());
    }
}
