import javax.xml.bind.annotation.XmlElement;
import java.util.*;
import java.util.stream.Collectors;

public class PetriNet {
    private List<Place> places;
    private List<Transition> transitions;
    private List<Arc> arcs;
    private String id;

    public void run() {
        List<Transition> enabledTransitions = transitions.stream().filter(transition -> transition.isEnabled(this)).collect(Collectors.toList());
        Map<Place, List<Transition>> concurrentTransitions = getPlacesWithConcurrentTransitions(enabledTransitions);
        removeConcurrentTransitionsFromEnabledTransitions(enabledTransitions, concurrentTransitions.values());
        enabledTransitions.forEach(transition -> transition.fire(this));
        fireConcurrentTransitions(concurrentTransitions);
        places.forEach(Place::setTokensAfterCycle);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean canRun() {
        return transitions.stream().anyMatch(transition -> transition.isEnabled(this));
    }

    private void fireConcurrentTransitions(Map<Place, List<Transition>> concurrentTransitions) {
        concurrentTransitions.forEach(this::fireConcurrentTransitions);
    }

    private void fireConcurrentTransitions(Place place, List<Transition> transitions){
        List<Transition> transitionsToBeFired = getConcurrentTransitionsToBeFired(transitions);
        while (transitionsToBeFired != null && transitionsToBeFired.size() > 0){
            Random random = new Random();
            int randomTransitionPosition = random.nextInt(transitionsToBeFired.size());
            transitionsToBeFired.get(randomTransitionPosition).fireOnlyOnce(this);
            transitionsToBeFired = getConcurrentTransitionsToBeFired(transitionsToBeFired);
        }
    }

    private List<Transition> getConcurrentTransitionsToBeFired(List<Transition> transitions){
        return transitions.stream().filter(
                transition -> transition.isEnabled(this)
        ).collect(Collectors.toList());
    }

    private void removeConcurrentTransitionsFromEnabledTransitions(List<Transition> enabledTransitions, Collection<List<Transition>> concurrentTransitions){
        concurrentTransitions.forEach(
                enabledTransitions::removeAll
        );
    }

    private Map<Place, List<Transition>> getPlacesWithConcurrentTransitions(List<Transition> enabledTransitions) {
        Map<Place, List<Transition>> placesWithConcurrentTransitions = new HashMap<>();
        for (Place place : places) {
            Set<Transition> concurrentTransitions = new HashSet<>();

            for (Transition transition : enabledTransitions) {
                for (Arc arc : transition.getInputArcs(this)) {
                    if (arc.getPlace(this).equals(place)){
                        concurrentTransitions.add(arc.getTransition(this));
                    }
                }
            }

            if (concurrentTransitions.size() > 1){
                placesWithConcurrentTransitions.put(place, new ArrayList<>(concurrentTransitions));
            }

        }
        return placesWithConcurrentTransitions;
    }

    public void initializeArcs() {
        List<Arc> clones;

        try {
            clones = arcs.stream().map(arc -> (Arc) arc.clone()).collect(Collectors.toList());
            setArcs(clones);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @XmlElement(name="arc")
    public List<Arc> getArcs() {
        return arcs;
    }

    public void setArcs(List<Arc> arcs) {
        this.arcs = arcs;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("------------------------------\n");

        result.append("Lugar (id)            ");
        for (Place place : places) {
            result.append(place.toString()).append(" ");
        }

        result.append("\n").append("Marcação              ");
        for (Place place : places) {
            result.append(place.getTokens()).append(" ");
        }

        result.append("\n\n").append("Transição (id)        ");
        for (Transition transition : transitions){
            result.append(transition.toString()).append(" ");
        }

        result.append("\n").append("Habilitada?           ");
        for (Transition transition : transitions){
            result.append(transition.isEnabledString(this)).append(" ");
        }

        result.append("\n------------------------------\n");

        return result.toString();

    }
    @XmlElement(name="place")
    public List<Place> getPlaces() {
        return places;
    }
    public void setPlaces(List<Place> places) {
        this.places = places;
    }
    @XmlElement(name="transition")
    public List<Transition> getTransitions() {
        return transitions;
    }
    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }
}
