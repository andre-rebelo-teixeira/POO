package population;

import java.util.Comparator;

class IndividualComparator implements Comparator<Individual> {
    @Override
    public int compare(Individual o1, Individual o2) {
        return Float.compare(o1.get_comfort_level(), o2.get_comfort_level());
    }
}