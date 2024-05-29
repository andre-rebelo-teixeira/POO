package population;

import ExponentialDistribution.ExponentialDistribution;
import ExponentialDistribution.ExponentialDistributionInterface;
import Pair.Pair;

import java.sql.Array;
import java.util.*;

import FixedSizePriorityQueue.FixedSizePriorityQueueInterface;
import FixedSizePriorityQueue.FixedSizePriorityQueue;


public class Population implements PopulationInterface, Observer
{
    private HashMap< Integer, Individual > individuals;
    private int[] best_distributions;

    private Integer current_id;
    private final Integer number_of_planetary_systems;
    private final Integer number_of_patrols;
    private final int[][] cost_matrix;
    private float t_min;
    private Boolean optimal_solution;

    private Pair< Float, String > best_individual_info;

    private ArrayList< Pair< Integer, Float > > comfort_map;
    private ExponentialDistributionInterface expo_random;

    public Population( Integer size,
                       Integer number_of_planetary_systems,
                       Integer number_of_patrols,
                       int[][] cost_matrix )
    {

        this.number_of_planetary_systems = number_of_planetary_systems;
        this.expo_random = new ExponentialDistribution( 0.0 );
        Vector< Integer > plannetary_system_vec = new Vector<>();
        this.number_of_patrols = number_of_patrols;
        this.individuals = new HashMap<>();
        this.cost_matrix = cost_matrix;
        this.optimal_solution = false;
        this.current_id = - 1;
        this.compute_min_time();
        this.comfort_map = new ArrayList<>();
        this.best_individual_info = new Pair< Float, String >( ( float ) - 1.0, "" );

        IndividualComparator individualComparator = new IndividualComparator();

        for ( int i = 0; i < size; i++ )
        {
            Individual ind = new Individual( this.get_new_id(),
                    this.number_of_planetary_systems,
                    this.number_of_patrols,
                    this.cost_matrix,
                    this.t_min );

            // Add individuals to where they should be
            this.individuals.put( ind.id, ind );

            // Add Data to the individuals
            ind.add_observer( this );
            ind.create_random_patrol_distribution();


        }

        for ( int i = 0; i < this.number_of_planetary_systems; i++ )
        {
            plannetary_system_vec.add( i );
        }

    }

    @Override
    public Boolean remove_one_individual( Integer individual_id )
    {
        Individual ind = this.individuals.get( individual_id );
        if ( ind != null )
        {
            this.individuals.remove( individual_id );
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove_individuals( ArrayList< Integer > individual_ids )
    {
        boolean all_removed = true;

        for ( Integer individual_id : individual_ids )
        {
            all_removed = all_removed && this.remove_one_individual( individual_id );
        }
        return all_removed;
    }

    @Override
    public Boolean remove_all_individuals()
    {
        this.individuals.clear();
        return true;
    }

    @Override
    public Boolean change_distribution_of_individual( Integer individual_id, Integer num_changes )
    {
        Individual ind = this.individuals.get( individual_id );

        if ( ind != null )
        {
            ind.change_distribution( num_changes );
            return true;
        }
        return false;
    }

    @Override
    public Boolean change_distribution_of_individuals( ArrayList< Pair< Integer, Integer > > individuals_changes )
    {
        boolean all_changed = true;
        for ( Pair< Integer, Integer > individual_change : individuals_changes )
        {
            all_changed = all_changed &&
                    this.change_distribution_of_individual( individual_change.getFirst(), individual_change.getSecond() );
        }
        return all_changed;
    }

    @Override
    public Integer create_new_copy_of_individual( Integer individual_id )
    {
        Individual ind;

        ind = this.individuals.get( individual_id );
        Integer new_id = this.get_new_id();

        // Simply puts, still need to shuffle the distributions around
        if ( ind != null )
        {
            Individual new_ind = new Individual( ind, new_id );
            this.individuals.put( new_id, new_ind );
            return new_id;
        }

        return - 1;
    }

    @Override
    public ArrayList< Integer > create_new_copy_of_individuals( ArrayList< Pair< Integer, Integer > > individuals_changes )
    {
        ArrayList< Integer > new_ids = new ArrayList<>();
        for ( Pair< Integer, Integer > individual_change : individuals_changes )
        {
            new_ids.add( this.create_new_copy_of_individual( individual_change.getFirst() ) );
        }
        return new_ids;
    }

    @Override
    public Integer get_population_size()
    {
        return this.individuals.size();
    }


    @Override
    public ArrayList< Pair< Integer, Float > > get_comfort_vector()
    {
        return this.comfort_map;
    }

    @Override
    public Float get_comfort_value( Integer individual_id )
    {
        return this.t_min / this.individuals.get( individual_id ).get_max_patrol_time();
    }

    @Override
    public Boolean get_optimal_solution_found()
    {
        return this.optimal_solution;
    }

    @Override
    public void start_new_epidemic()
    {
        System.out.println( "Started an epidemic with " + this.individuals.size() );
        ArrayList< Integer > individual_ids = new ArrayList<>();

        for ( Individual individual : this.individuals.values() )
        {
            individual_ids.add( individual.getId() );
        }

        ArrayList< Individual > temp = new ArrayList<>();
        FixedSizePriorityQueueInterface< Individual > best_individuals = this.create_priority_queue( 5 );

        while ( best_individuals.peek() != null )
        {
            Individual ind = best_individuals.poll();
            temp.add( ind );
            individual_ids.remove( ind.getId() );
        }

        Random rand = new Random();

        ArrayList< Integer > remove_individual_ids = new ArrayList<>();

        for ( Integer individual_id : individual_ids )
        {
            Individual ind = this.individuals.get( individual_id );

            if ( ! ( 2.0 / 3.0 * ind.get_comfort_level() * 100 < rand.nextDouble( 0, 100 ) ) )
            {
                remove_individual_ids.add( individual_id );
            }
        }

        for ( Integer individual_id : remove_individual_ids )
        {
            this.individuals.remove( individual_id );
        }

    }

    @Override
    public String[] get_best_individuals_string()
    {
        ArrayList< Individual > temp = new ArrayList<>();
        FixedSizePriorityQueueInterface< Individual > best_individuals = this.create_priority_queue( 6 );
        if (best_individuals.peek() != null &&
                Objects.equals( best_individuals.peek().get_information_string(), this.best_individual_info.getSecond() ) ) {
            best_individuals.poll();

        }
        String[] best_individuals_string = new String[best_individuals.size()];

        int counter = 0;
        while ( best_individuals.peek() != null && counter < 5)
        {
            Individual ind = best_individuals.poll();
            best_individuals_string[counter++] = ind.get_information_string();
        }
        return best_individuals_string;
    }

    @Override
    public Float get_time_min()
    {
        return this.t_min;
    }

    @Override
    public Pair< Float, String > get_best_individual_values()
    {
        return this.best_individual_info;
    }

    @Override
    public void update( int individualId, float comfortValue )
    {
        if ( this.best_individual_info.getFirst() < comfortValue )
        {
            Individual ind;
            ind = this.individuals.get( individualId );
            this.best_individual_info = new Pair< Float, String >( comfortValue, ind.get_information_string());
        }

        Pair< Integer, Float > new_pair = new Pair< Integer, Float >( individualId, comfortValue );

        if ( comfortValue == 1 )
        {
            this.optimal_solution = true;
        }

        // Remove old value from the map
        for ( Pair< Integer, Float > pair : this.comfort_map )
        {
            if ( pair.getFirst() == individualId )
            {
                this.comfort_map.remove( pair ); // remove previous pair
                break;
            }
        }

        //  Add new value to the map
        this.comfort_map.add( new Pair< Integer, Float >( individualId, comfortValue ) );
        return;
    }


    private Integer get_new_id()
    {
        this.current_id = this.current_id + 1;
        return this.current_id;
    }

    private void compute_min_time()
    {
        int sum = 0;

        for ( int i = 0; i < this.number_of_planetary_systems; i++ )
        {
            int min_patrol_time = Integer.MAX_VALUE;
            for ( int j = 0; j < this.number_of_patrols; j++ )
            {
                min_patrol_time = Math.min( min_patrol_time, cost_matrix[j][i] );
            }

            sum += min_patrol_time;
        }


        this.t_min = ( float ) sum / this.number_of_patrols;
    }

    /**
     * Create a Fixed Size priority Queue with the best individuals of the current set of individuals
     *
     * @param size Integer value that species the desired size of the Priority Queue
     * @return Desired Priority Queue
     */
    private FixedSizePriorityQueueInterface< Individual > create_priority_queue( Integer size )
    {
        IndividualComparator individualComparator = new IndividualComparator();
        FixedSizePriorityQueueInterface< Individual > q = new FixedSizePriorityQueue<>( size, individualComparator );

        // Maybe change for q.addAll in the future
        for ( Individual individual : this.individuals.values() )
        {
            q.add( individual );
        }
        System.out.println();

        return q;
    }
}
