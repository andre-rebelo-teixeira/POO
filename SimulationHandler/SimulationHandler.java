package SimulationHandler;

import java.util.*;

import ExponentialDistribution.ExponentialDistributionInterface;
import ExponentialDistribution.ExponentialDistribution;

// Import simulation Data
import Pair.Pair;
import SimulationData.SimulationDataInterface;

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

/**
 * SimulationHandler.java
 *
 * This class handles the simulation of events, managing the population and
 * event container,
 * updating statistics, and printing observations.
 *
 * Created on 01/06/2024
 *
 * @version 1.0
 * @since 1.0
 *        a * @see population.Population
 * @see Event.GenericEvent
 * @see Event.PEC
 * @see CustomEvents.ReproductionEvent
 * @see CustomEvents.MutationEvent
 * @see CustomEvents.DeathEvent
 * @see population.PopulationInterface
 *
 */
public class SimulationHandler implements SimulationHandlerInterface {
    // Event and Observation variables
    private static final int PRINT_FREQUENCY = 20;

    private final ExponentialDistributionInterface exp_distribution;
    private final int maxTime;
    private int presentInstant;
    private int observationNumber;
    private int populationSize;
    private int numbEpidemics;
    private int ev_count;
    private float emp_police_time;

    private PEC event_container;
    private SimulationDataInterface simulation_data;
    private PopulationInterface population;

    /**
     * Constructor for the SimulationHandler class.
     *
     * @param simulation_data The simulation data.
     */
    public SimulationHandler(SimulationDataInterface simulation_data) {
        this.simulation_data = simulation_data;
        this.maxTime = simulation_data.getFinalInstance();
        this.presentInstant = 0;
        this.exp_distribution = new ExponentialDistribution(0.0);
        this.ev_count = 0;
        this.emp_police_time = 0;

        // Start population
        this.population = new Population(simulation_data.getInitialPopulationSize(),
                simulation_data.get_numb_planets(),
                simulation_data.get_numb_patrols(),
                simulation_data.getcostMatrix());

        // Start the event container
        this.event_container = new PEC();

        ArrayList<Pair<Integer, Float>> conform_vector = this.population.get_comfort_vector();
        Collections.shuffle(conform_vector);

        double time_counter = 0;

        // Create all the start events for this class

        // Create all the Death events
        for (Pair<Integer, Float> pair : conform_vector) {
            GenericEvent e = new DeathEvent(pair.first(), this.simulation_data.getMu());
            this.exp_distribution.setMean(e.get_mean_time(pair.second()));
            double rand_time = exp_distribution.getExponentialRandom();
            e.setHandling_time(rand_time + time_counter);
            this.event_container.addEvent(e);
            double sum = rand_time + time_counter;
            time_counter += rand_time;
        }

        // New shuffle for the reproduction events
        Collections.shuffle(conform_vector);
        time_counter = 0;

        for (Pair<Integer, Float> pair : conform_vector) {
            double rand_time = exp_distribution.getExponentialRandom();
            GenericEvent e = new ReproductionEvent(pair.first(),
                    this.simulation_data.getM(),
                    this.simulation_data.getMu(),
                    this.simulation_data.getSigma(),
                    this.simulation_data.getPhi(),
                    pair.second(),
                    this.simulation_data.get_numb_planets(),
                    this.exp_distribution);
            this.exp_distribution.setMean(e.get_mean_time(pair.second()));
            e.setHandling_time(rand_time + time_counter);
            this.event_container.addEvent(e);
            double sum = rand_time + time_counter;
            time_counter += rand_time;
        }

        // New shuffle for the mutation events
        Collections.shuffle(conform_vector);
        time_counter = 0;
        Random rand = new Random();

        for (Pair<Integer, Float> pair : conform_vector) {
            double rand_time = exp_distribution.getExponentialRandom();
            GenericEvent e = new MutationEvent(
                    pair.first(),
                    this.simulation_data.get_numb_planets(),
                    this.simulation_data.getPhi(),
                    this.exp_distribution);
            this.exp_distribution.setMean(e.get_mean_time(pair.second()));
            e.setHandling_time(rand_time + time_counter);
            this.event_container.addEvent(e);
            double sum = rand_time + time_counter;
            time_counter += rand_time;
        }
    }

    /**
     * Updates the simulation statistics.
     */
    @Override
    public void updateStats() {
        this.presentInstant++;
        this.observationNumber++;
        this.populationSize = this.population.get_population_size();
        Map<String, Integer> event_counter = this.event_container.getEventCounter();
        int all_event_counter = 0;

        for (String  s  : event_counter.keySet()) {
            Integer count = event_counter.get(s);
            all_event_counter += count;
        }


        /*

        for (int i = 0; i < event_counter.size(); i++) {
            String key = event_counter.keySet().toArray()[i].toString();
            Integer count = event_counter.get(key);
            all_event_counter += count;
        }*/

        this.ev_count = all_event_counter;

        if (this.simulation_data.get_max_individuals() < this.population.get_population_size()) {
            this.numbEpidemics += 1;
        }
        this.emp_police_time = this.population.get_time_min();
    }

    /**
     * Starts the simulation, incrementing time and handling events.
     */
    @Override
    public void start() {
        for (int i = 1; i <= maxTime; i++) {
            // Check end conditions
            if (this.event_container.get_num_events() == 0) {
                break;
            }
            if (this.event_container.peekEvent() == null) {
                break;
            }
            if (this.event_container.peekEvent() != null
                    && event_container.peekEvent().getHandling_time() > this.simulation_data.getTau()) {
                break;
            }
            if (this.simulation_data.get_max_individuals() < this.population.get_population_size()) {
                this.population.start_new_epidemic();
                this.numbEpidemics++;
            }

            // Handle the events
            Queue<GenericEvent> events = this.event_container.getEventQueue(i);
            if (events == null) {
                continue;
            }
            for (GenericEvent event : events) {
                Pair<PopulationInterface, PEC> pair = event.handle(this.population, this.event_container);
                this.event_container = pair.second();
                this.population = pair.first();
            }
            updateStats();
            if (i % PRINT_FREQUENCY == 0) {
                print_simulation_observation();
            }
        }
        print_simulation_observation();
    }

    /**
     * Prints the current simulation observation.
     */
    @Override
    public void print_simulation_observation() {
        String[] best_individual_string = this.population.get_best_individuals_string();

        if (best_individual_string.length == 0) {
            best_individual_string = new String[] { "<No individual to show here>" };
        }

        System.out.println("Observation " + this.observationNumber + ": "
                + "\n\t\t\tPresent Instant: " + this.presentInstant
                + "\n\t\t\tNumber of realized events: " + this.ev_count
                + "\n\t\t\tPopulation size: " + this.populationSize
                + "\n\t\t\tNumber of epidemics: " + this.numbEpidemics
                + "\n\t\t\tBest distribution of the patrols: "
                + this.population.get_best_individual_values().second().split(":")[0]
                + "\n\t\t\tEmpire policing time: "
                + this.population.get_best_individual_values().second().split(":")[1]
                + "\n\t\t\tComfort: " + this.population.get_best_individual_values().first()
                + "\n\t\t\tOther candidate distributions: " + best_individual_string[0]);

        for (int i = 1; i < best_individual_string.length && i < 5; i++) {
            if (best_individual_string[i] != null)
                System.out.println("\t\t\t" + "                               " + best_individual_string[i]);
        }

        System.out.print("\n");
    }
}
