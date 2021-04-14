import javax.xml.bind.annotation.XmlElement;

public class Arc {

    private Transition transition;
    private Place place;
    private int weight;
    private String sourceId;
    private String destinationId;

    public Transition getTransition() {
        return transition;
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

    public void setTransition(Transition transition) {
        this.transition = transition;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
    @XmlElement(name = "multiplicity")
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean canFire() {
        return place.getTokens().size() >= weight;
    }

    public void fireInputArc() {
        for (int i = 0; i < weight; i++) {
            place.getTokens().pop();
        }
    }

    public void fireOutputArc() {
        for (int i = 0; i < weight; i++) {
            Token token = new Token();
            place.getTokens().push(token);
        }
    }

}
