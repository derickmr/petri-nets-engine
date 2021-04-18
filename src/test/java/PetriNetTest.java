import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PetriNetTest {

    @Test
    public void testFireFromOnePlaceToOther(){
        PetriNet petriNet = new PetriNet();

        Place place = new Place();
        Place place2 = new Place();

        Transition transition = new Transition();
        transition.setId("1");

        place.setId("1");
        place.setTokens(2);

        place2.setId("2");
        place2.setTokens(0);

        Arc inputArc = new Arc();
        inputArc.setTransition(transition);
        inputArc.setPlace(place);
        inputArc.setWeight(1);
        transition.setInputArcs(Arrays.asList(inputArc));

        Arc outputArc = new Arc();
        outputArc.setTransition(transition);
        outputArc.setPlace(place2);
        outputArc.setWeight(4);
        transition.setOutputArcs(Arrays.asList(outputArc));

        petriNet.setPlaces(Arrays.asList(place, place2));
        petriNet.setTransitions(Arrays.asList(transition));

        System.out.println(petriNet.toString());

        petriNet.run();
        System.out.println(petriNet.toString());

        assertEquals(1, place.getTokens());
        assertEquals(4, place2.getTokens());

        petriNet.run();
        System.out.println(petriNet.toString());

        assertEquals(0, place.getTokens());
        assertEquals(8, place2.getTokens());

    }

}
