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

    private final int maxTime;
    private int presentInstant;
    private int observationNumber;
    private int populationSize;
    private int numbEpidemics;
    private int ev_count;
    private float emp_police_time;

    private PEC event_container;

    SimulationData simulation_data;
    PopulationInterface population;

    public SimulationHandler(SimulationData simulation_data) {
        this.simulation_data = simulation_data;
        this.maxTime = simulation_data.getFinalInstance();
        this.presentInstant = 0;
        this.exp_distribution = new ExponentialDistribution(0.0);
        this.ev_count = 0;
        this.emp_police_time = 0;


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

        // Create all the Death events
        for (Pair<Integer, Float> pair : conform_vector) {

            GenericEvent e = new DeathEvent(pair.getFirst(), this.simulation_data.getMu());
            this.exp_distribution.setMean(e.get_mean_time(pair.getSecond()));
            double rand_time = exp_distribution.getExponentialRandom();
            e.setHandling_time(rand_time + time_counter);
            this.event_container.addEvent(e);

            double sum = rand_time + time_counter;
            System.out.println("Death time for id " + pair.getFirst() + " is " + sum);
            time_counter += rand_time;
        }

        // New Shuffle for the reproduction events
        Collections.shuffle(conform_vector);
        time_counter = 0;

        for (Pair<Integer, Float> pair : conform_vector) {
            double rand_time = exp_distribution.getExponentialRandom();

            GenericEvent e = new ReproductionEvent(pair.getFirst(),
                    this.simulation_data.getM(),
                    this.simulation_data.getMu(),
                    this.simulation_data.getSigma(),
                    this.simulation_data.getPhi(),
                    pair.getSecond(),
                    this.simulation_data.get_numb_planets(),
                    this.exp_distribution);

            this.exp_distribution.setMean(e.get_mean_time(pair.getSecond()));
            e.setHandling_time(rand_time + time_counter);
            this.event_container.addEvent(e);

            double sum = rand_time + time_counter;
            System.out.println("Reproduction time for id " + pair.getFirst() + " is " + sum);

            time_counter += rand_time;
        }

        // New Shuffle for the mutation Events
        Collections.shuffle(conform_vector);
        time_counter = 0;
        Random rand = new Random();

        for (Pair<Integer, Float> pair : conform_vector) {
            double rand_time = exp_distribution.getExponentialRandom();

            GenericEvent e = new MutationEvent(
                    pair.getFirst(),
                    this.simulation_data.get_numb_planets(),
                    this.simulation_data.getPhi(),
                    this.exp_distribution);
            this.exp_distribution.setMean(e.get_mean_time(pair.getSecond()));
            e.setHandling_time(rand_time + time_counter);
            this.event_container.addEvent(e);

            double sum = rand_time + time_counter;
            System.out.println("Mutation time for id " + pair.getFirst() + " is " + sum);

            time_counter += rand_time;
        }

    }

    public void updateStats() {
        this.presentInstant++;
        this.observationNumber++;
        this.populationSize = this.population.get_population_size();
        Map<String, Integer> event_counter = this.event_container.getEventCounter();
        int all_event_counter = 0;
        for (int i = 0; i < event_counter.size(); i++) {
            String key = event_counter.keySet().toArray()[i].toString();
            Integer count = event_counter.get(key);
            all_event_counter += count;
        }
        this.ev_count = all_event_counter;

        if (this.simulation_data.get_max_individuals() < this.population.get_population_size()) {
            this.numbEpidemics += 1;
        }
        this.emp_police_time = this.population.get_time_min();
    }

    // Increment time
    public void start() {
        for (int i = 1; i <= maxTime; i++) {
            System.out.println("iteration " + i +  " queue size " + this.event_container.size() + " population size " + this.population.get_population_size() );
            // check end conditions
            // No more events in the PEc
            if (this.event_container.get_num_events() == 0) {
                break;
            }

            // No more events
            if (this.event_container.peekEvent() == null) {
                break;
            }

            // Event Timer is outside the max time the simulation
            if (this.event_container.peekEvent() != null
                    && event_container.peekEvent().getHandling_time() > this.simulation_data.getTau()) {
                break;
            }

            // Start en epidemic
            if (this.simulation_data.get_max_individuals() < this.population.get_population_size()) {
                this.population.start_new_epidemic();
                this.numbEpidemics++;
            }

            // Individual with Comfort 1

            // Handle the events
            Queue<GenericEvent> events = this.event_container.getEventQueue(i);
            if (events == null) {
                continue;
            }

            for (GenericEvent event : events) {
                Pair<PopulationInterface, PEC> pair = event.handle(this.population, this.event_container);
                this.event_container = pair.getSecond();
                this.population = pair.getFirst();
            }

            updateStats();
            if (i % PRINT_FREQUENCY == 0) {
                print_simulation_observation();
            }
        }

        print_simulation_observation();

        Map<String, Integer> event_counter = this.event_container.getEventCounter();
        for (int i = 0; i < event_counter.size(); i++) {
            String key = event_counter.keySet().toArray()[i].toString();
            Integer count = event_counter.get(key);

            System.out.println("Event of type : " + key + " happened " + count);

        }

        System.out.println("Num individuals " + this.population.get_population_size());
    }

    public void print_simulation_observation() {

        String[] best_individual_string = this.population.get_best_individuals_string();
        // No individuals
        if (best_individual_string.length == 0) {

            best_individual_string = new String[] { "<No individual to show here2>" };
        }

        System.out.println("Observation " + this.observationNumber + ": "
                + "\n\t\t\tPresent Instant: " + this.presentInstant
                + "\n\t\t\tNumber of realized events: " + this.ev_count
                + "\n\t\t\tPopulation size: " + this.populationSize
                + "\n\t\t\tNumber of epidemics: " + this.numbEpidemics
                + "\n\t\t\tBest distribution of the patrols: "
                + this.population.get_best_individual_values().getSecond().split(":")[0]
                + "\n\t\t\tEmpire policing time: " + this.population.get_best_individual_values().getSecond().split(":")[1]
                + "\n\t\t\tComfort: " + this.population.get_best_individual_values().getFirst()
                + "\n\t\t\tOther candidate distributions: " + best_individual_string[0]);

        for (int i = 1; i < best_individual_string.length && i < 5; i++) {
            System.out.println("\t\t\t" + "                               " + best_individual_string[i]);
        }

        System.out.print("\n\n");
    }
}
