package population;

import java.util.Comparator;

class IndividualComparator implements Comparator<Individual> {
    @Override
    public int compare(Individual o1, Individual o2) {
        return o1.getId().compareTo(o2.get_max_patrol_time());
    }
}