import java.util.List;

public class PetriNet {

    List<Place> places;
    List<Transition> transitions;

    public void run() {
        //TODO
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Lugar            ");
        for (Place place : places) {
            result.append(place.toString()).append(" ");
        }

        result.append("\n").append("Marcação         ");
        for (Place place : places) {
            result.append(place.getTokens().size()).append(" ");
        }

        result.append("\n").append("Transição        ");
        for (Transition transition : transitions){
            result.append(transition.toString()).append(" ");
        }

        result.append("\n").append("Habilitada?      ");
        for (Transition transition : transitions){
            result.append(transition.isEnabledString()).append(" ");
        }

        return result.toString();

    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }
}
