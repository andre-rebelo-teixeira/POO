package SimulationHandler;

import java.util.*;

import ExponentialDistribution.ExponentialDistributionInterface;
import ExponentialDistribution.ExponentialDistribution;

// Import simulation Data
import Pair.Pair;
import SimulationData.SimulationData;

// Import Population
import population.Population;

// Generic event and PEC
import Event.GenericEvent;
import Event.PEC;

// Import custom Events
import CustomEvents.ReproductionEvent;
import CustomEvents.MutationEvent;
import CustomEvents.DeathEvent;
import population.PopulationInterface;

public class SimulationHandler {
    // Event and Observation variables
    private static final int PRINT_FREQUENCY = 20;

    private final ExponentialDistributionInterface exp_distribution;

    private final int  maxTime;
    private int presentInstant;
    private int observationNumber;
    private int populationSize;
    private int numbEpidemics;

    private PEC event_container;

    SimulationData simulation_data;
    PopulationInterface population;


    public SimulationHandler(SimulationData simulation_data){
        this.simulation_data = simulation_data;
        this.maxTime = simulation_data.getFinalInstance();
        this.presentInstant = 0;
        this.exp_distribution = new ExponentialDistribution(0.0);

        // start population
        this.population = new Population(simulation_data.getInitialPopulationSize(),
                                            simulation_data.get_numb_planets(),
                                            simulation_data.get_numb_patrols(),
                                            simulation_data.getcostMatrix());
        // start the event container
        this.event_container = new PEC();

        ArrayList<Pair<Integer, Float>> conform_vector = this.population.get_comfort_vector();

        Collections.shuffle(conform_vector);

        double time_counter = 0;

        // Create all the start event for this class
/*

        // Create all the Death events
        for (Pair<Integer, Float> pair : conform_vector) {
            this.exp_distribution.setLambda((1 -  Math.log(1 - pair.getSecond())) * simulation_data.getMu());
            double rand_time = exp_distribution.getExponentialRandom((float)1.0);
            this.event_container.addEvent(new DeathEvent(pair.getFirst(),  rand_time +  time_counter));
            time_counter += rand_time;
        } */

        // New Shuffle for the reproduction events
        Collections.shuffle(conform_vector);
        time_counter = 0;

        for (Pair<Integer, Float> pair : conform_vector) {
            this.exp_distribution.setLambda( (1 - Math.log(pair.getSecond())  * simulation_data.getM()));
            double rand_time = exp_distribution.getExponentialRandom((float) 1.0);
            Double num_changes =  (double)(1 - pair.getSecond()) * simulation_data.getM();
            this.event_container.addEvent(new ReproductionEvent(pair.getFirst(), rand_time + time_counter,  num_changes.intValue()));
            time_counter += rand_time;
        }

        // New Shuffle for the mutation Events
        Collections.shuffle(conform_vector);
        time_counter = 0;
        Random rand = new Random();
/*
        for (Pair <Integer, Float> pair : conform_vector) {
            this.exp_distribution.setLambda(1 - Math.log(pair.getSecond()) * simulation_data.getSigma());
            double rand_time = exp_distribution.getExponentialRandom((float)1.0);
            this.event_container.addEvent(new MutationEvent(pair.getFirst(),
                                                        rand_time + time_counter,
                                                            rand.nextInt(this.simulation_data.get_numb_planets()) ));
            time_counter += rand_time;
        }
*/
    }

    public void updateStats(){
        this.presentInstant++;
        this.observationNumber++;
        this.populationSize = this.population.get_population_size();
    }

    // Increment time
    // Request population step()
    // create all events that come on event_vector
    public void start(){
        for (int i = 1; i <= maxTime; i++){

            // check end conditions
            // No more events in the PEc
            if (this.event_container.get_num_events() == 0) {
                System.out.println("Out of events");
                break;
            }

            // No more events
            if (this.event_container.peekEvent() == null) {
                break;
            }

            // Event Timer is outside the max time the simulation
            if (this.event_container.peekEvent() != null && event_container.peekEvent().getHandling_time() > this.simulation_data.getTau())
            {
                break;
            }

            // Start en epidemic
            if (this.simulation_data.get_max_individuals() <  this.population.get_population_size()) {
                this.population.start_new_epidemic();
            }

            //  Individual with Comfort 1

            updateStats();
            if( i % PRINT_FREQUENCY == 0){
                print_simulation_observation();
            }

            //  Handle the events
            Queue<GenericEvent> events = this.event_container.getEventQueue(i);
            if (events == null) {
                continue;
            }

            for (GenericEvent event : events) {
                this.event_container.setEventCounter(event.handle(this.event_container.getEventCounter(), this.population, this.event_container));
            }
        }

        print_simulation_observation();

        Map<String, Integer> event_counter = this.event_container.getEventCounter();
        for (int i = 0; i < event_counter.size(); i++) {
            String key = event_counter.keySet().toArray()[i].toString();
            Integer count = event_counter.get(key);

            System.out.println("Event of type : "  + key + " happened " +  count);

        }

        System.out.println("Num individuals " + this.population.get_population_size());
    }

    public void print_simulation_observation(){
        Map<String, Integer> event_counter = this.event_container.getEventCounter();
        Integer count = event_counter.values().iterator().next();


        System.out.println("Observation "+ this.observationNumber +": "
            + "\n\t\t\tPresent Instant: " + this.presentInstant
            + "\n\t\t\tNumber of realized events: " + count
            + "\n\t\t\tPopulation size: " + this.populationSize
            + "\n\t\t\tNumber of epidemics: " + this.numbEpidemics
            + "\n\t\t\tBest distribution of the patrols: " 
            + "\n\t\t\tEmpire policing time: " + this.population.get_time_min()
            + "\n\t\t\tComfort: " + 0 // change latter
            + "\n\t\t\tOther candidate distributions " + Arrays.toString(this.population.get_best_individuals_string())
            + "\n\n");

    }
}
