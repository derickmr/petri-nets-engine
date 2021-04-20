import javax.xml.bind.annotation.XmlElement;
import java.util.*;
import java.util.stream.Collectors;

public class PetriNet {
    List<Place> places;
    List<Transition> transitions;
    private List<Arc> arcs;

    public void run() {
        List<Transition> enabledTransitions = transitions.stream().filter(Transition::isEnabled).collect(Collectors.toList());
        Map<Place, List<Transition>> concurrentTransitions = getPlacesWithConcurrentTransitions(enabledTransitions);
        removeConcurrentTransitionsFromEnabledTransitions(enabledTransitions, concurrentTransitions.values());
        enabledTransitions.forEach(Transition::fire);
        fireConcurrentTransitions(concurrentTransitions);
        places.forEach(Place::setTokensAfterCycle);
    }

    private void fireConcurrentTransitions(Map<Place, List<Transition>> concurrentTransitions) {
        concurrentTransitions.forEach(this::fireConcurrentTransitions);
    }

    private void fireConcurrentTransitions(Place place, List<Transition> transitions){
        List<Transition> transitionsToBeFired = getConcurrentTransitionsToBeFired(transitions);
        while (transitionsToBeFired != null && transitionsToBeFired.size() > 0){
            Random random = new Random();
            int randomTransitionPosition = random.nextInt(transitionsToBeFired.size());
            transitionsToBeFired.get(randomTransitionPosition).fireOnlyOnce();
            transitionsToBeFired = getConcurrentTransitionsToBeFired(transitionsToBeFired);
        }
    }

    private List<Transition> getConcurrentTransitionsToBeFired(List<Transition> transitions){
        return transitions.stream().filter(
                Transition::isEnabled
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
            List<Transition> concurrentTransitions = new ArrayList<>();

            for (Transition transition : enabledTransitions) {
                for (Arc arc : transition.getInputArcs()) {
                    if (arc.getPlace().equals(place)){
                        concurrentTransitions.add(arc.getTransition());
                    }
                }
            }

            if (concurrentTransitions.size() > 1){
                placesWithConcurrentTransitions.put(place, concurrentTransitions);
            }

        }
        return placesWithConcurrentTransitions;
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

        result.append("Lugar            ");
        for (Place place : places) {
            result.append(place.toString()).append(" ");
        }

        result.append("\n").append("Marcação         ");
        for (Place place : places) {
            result.append(place.getTokens()).append(" ");
        }

        result.append("\n\n").append("Transição        ");
        for (Transition transition : transitions){
            result.append(transition.toString()).append(" ");
        }

        result.append("\n").append("Habilitada?      ");
        for (Transition transition : transitions){
            result.append(transition.isEnabledString()).append(" ");
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
