package com.unisinos.petri;
public class InhibitorArc extends Arc {

    @Override
    public boolean canFire(PetriNet petriNet) {
        return getPlace(petriNet).getTokens() < getWeight();
    }

    @Override
    public void fireInputArc(PetriNet petriNet) {
        //do nothing
    }

}
