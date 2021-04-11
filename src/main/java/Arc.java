public class Arc {

    private Transition transition;
    private Place place;
    private int weight;

    public Transition getTransition() {
        return transition;
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
