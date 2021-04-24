public class ResetArc extends Arc {

    @Override
    public void fireInputArc(PetriNet petriNet) {
        Place place = getPlace(petriNet);
        place.setTokens(0);
    }

}
