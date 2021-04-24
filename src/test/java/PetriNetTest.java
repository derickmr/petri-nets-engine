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
        transition.setId("10");

        place.setId("1");
        place.setTokens(2);

        place2.setId("2");
        place2.setTokens(0);

        Arc inputArc = new Arc();
        inputArc.setWeight(1);
        inputArc.setSourceId(place.getId());
        inputArc.setDestinationId(transition.getId());

        Arc outputArc = new Arc();
        outputArc.setWeight(4);
        outputArc.setSourceId(transition.getId());
        outputArc.setDestinationId(place2.getId());

        petriNet.setPlaces(Arrays.asList(place, place2));
        petriNet.setTransitions(Arrays.asList(transition));
        petriNet.setArcs(Arrays.asList(inputArc, outputArc));

        System.out.println(petriNet.toString());

        petriNet.run();
        System.out.println(petriNet.toString());

        assertEquals(0, place.getTokens());
        assertEquals(8, place2.getTokens());

    }

}
