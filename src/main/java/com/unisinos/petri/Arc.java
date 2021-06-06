package com.unisinos.petri;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

public class Arc {

    private int weight;
    private String sourceId;
    private String destinationId;
    private String type;

    public Transition getTransition(PetriNet petriNet) {
        return petriNet.getTransitions().stream()
            .filter( transition -> transition.getId().equals(getSourceId()) || transition.getId().equals(getDestinationId()))
            .collect(Collectors.toList())
            .get(0);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Place getPlace(PetriNet petriNet) {
        return petriNet.getPlaces().stream()
            .filter( place -> place.getId().equals(getSourceId()) || place.getId().equals(getDestinationId()))
            .collect(Collectors.toList())
            .get(0);
    }

    @XmlElement(name = "multiplicity")
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean canFire(PetriNet petriNet) {
        return getPlace(petriNet).getTokens() >= weight;
    }

    public void fireInputArc(PetriNet petriNet) {
        Place place = getPlace(petriNet);
        place.setTokens(place.getTokens() - weight);
    }

    public void fireOutputArc(PetriNet petriNet) {
        Place place = getPlace(petriNet);
        place.setTokensToBeAdded(weight);
    }

    @Override
    protected Object clone() {
        Arc clone;

        if (type.equalsIgnoreCase("inhibitor")){
            clone = new InhibitorArc();
        }
        else if (type.equalsIgnoreCase("reset")){
            clone = new ResetArc();
        }
        else {
            return this;
        }

        clone.setDestinationId(destinationId);
        clone.setSourceId(sourceId);
        clone.setWeight(weight);

        return clone;
    }
}
