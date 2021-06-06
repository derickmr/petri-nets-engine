package com.unisinos.petri;
import javax.xml.bind.annotation.XmlElement;

import java.util.*;
import java.util.stream.Collectors;

public class PetriNet {
    private List<Place> places;
    private List<Transition> transitions;
    private List<Arc> arcs;
    private String id;
    private boolean isInitialized = false;
    private int cycle = 0;

    public PetriNet() {}

    public PetriNet(List<Place> places, List<Transition> transitions, List<Arc> arcs) {
        this.places = places;
        this.transitions = transitions;
        this.arcs = arcs;
    }

    public void run() {
        List<Transition> enabledTransitions = transitions.stream().filter(transition -> transition.isEnabled(this)).collect(Collectors.toList());
        Map<Place, List<Transition>> concurrentTransitions = getPlacesWithConcurrentTransitions(enabledTransitions);
        removeConcurrentTransitionsFromEnabledTransitions(enabledTransitions, concurrentTransitions.values());
        enabledTransitions.forEach(transition -> transition.fire(this));
        fireConcurrentTransitions(concurrentTransitions);
        places.forEach(Place::setTokensAfterCycle);
        setCycle(cycle + 1);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean canRun() {
        if (!isInitialized){
            initializeArcs();
            setInitialized(true);
        }
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

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("------------------------------\n");

        result.append("Ciclo: " + cycle + "\n\n");

        result.append("Lugar            ");
        for (Place place : places) {
            result.append(place.toString());
        }

        result.append("\n").append("Marcação         ");
        for (Place place : places) {
            result.append(String.format("%5s", place.getTokens()));
        }

        result.append("\n\n").append("Transição        ");
        for (Transition transition : transitions){
            result.append(transition.toString());
        }

        result.append("\n").append("Habilitada?      ");
        for (Transition transition : transitions){
            result.append(transition.isEnabledString(this));
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

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }
}
