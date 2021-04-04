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
        //TODO
        return true;
    }

    public void fire() {
        //TODO
    }
}
