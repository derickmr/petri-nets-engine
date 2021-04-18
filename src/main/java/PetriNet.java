import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PetriNet {
    List<Place> places;
    List<Transition> transitions;
    private List<Arc> arcs;

    public void run() {
        List<Transition> executableTransitions = transitions.stream().filter(Transition::isEnabled).collect(Collectors.toList());
        executableTransitions.forEach(Transition::fire);
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
