package SimulationHandler;

import java.util.ArrayList;
import java.util.Vector;

import SimulationData.SimulationData;
import population.Population;
import Event.GenericEvent;


public class SimulationHandler {
    // Event and Observation variables
    private static final int PRINT_FREQUENCY = 20;
    private int maxTime;
    private int realizedEvents;
    private int presentInstant;
    private int observationNumber;
    private int populationSize;
    private int numbEpidemics;
    private int policingTime;
    private int confort;

    private GenericEvent eventHandler;
    private ArrayList<GenericEvent> events;

    
    int numberOfEvents;

    SimulationData simulation_data;
    Population population;


    Vector<Integer> confort_vector = new Vector<>();

    // Criar population
    // Criar eventos
    public SimulationHandler(SimulationData simulation_data){

        this.simulation_data = simulation_data;
        this.maxTime = simulation_data.getFinalInstance();
        this.presentInstant = 0;
        this.realizedEvents = 0;
        this.population = new Population(simulation_data.getInitialPopulationSize(), 
                                        simulation_data.get_numb_planets(), 
                                        simulation_data.get_numb_patrols(), 
                                        simulation_data.getcostMatrix());
    }

    public void create_events(){
    }

    public void updateStats(){
        this.presentInstant++;
        this.observationNumber++;
        this.populationSize = this.population.getPopSize();

    }

    // Increment time
    // Request population step()
    // create all events that come on event_vector
    public void start(){
        
        for (int i = 1; i <= maxTime; i++){
            if(this.confort == 1){
                break;
            }
            System.out.println("Testing pop"+this.population.getPopSize());
            System.out.println(this.population);
            

            updateStats();
            if( i % PRINT_FREQUENCY == 0){
                print_simulation_observation();
            }
        }
    }

    public void print_simulation_observation(){
        System.out.println("Obsevation "+ this.observationNumber +": "
            + "\n\t\t\tPresent Instant: " + this.presentInstant
            + "\n\t\t\tNumber of realized events: " + this.realizedEvents
            + "\n\t\t\tPopulation size: " + this.populationSize
            + "\n\t\t\tNumber of epidemics: " + this.numbEpidemics
            + "\n\t\t\tBest distribution of the patrols: " 
            + "\n\t\t\tEmpire policing time: " + this.policingTime
            + "\n\t\t\tComfort: " + this.confort
            + "\n\t\t\tOther candidate distributions "
            + "\n\n");

    }
}
