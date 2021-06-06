import org.junit.Test;

import java.util.Arrays;

import com.unisinos.petri.Arc;
import com.unisinos.petri.PetriNet;
import com.unisinos.petri.Place;
import com.unisinos.petri.Transition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testSimpleConflictScenario() {
        Place concurrentPlace = new Place();
        concurrentPlace.setId("1");
        concurrentPlace.setTokens(1);

        Transition transition = new Transition();
        transition.setId("2");

        Transition transition2 = new Transition();
        transition2.setId("3");

        Place place2 = new Place();
        place2.setId("4");

        Place place3 = new Place();
        place3.setId("5");

        Arc arc = new Arc();
        arc.setWeight(1);
        arc.setSourceId("1");
        arc.setDestinationId("2");

        Arc arc2 = new Arc();
        arc2.setWeight(1);
        arc2.setSourceId("1");
        arc2.setDestinationId("3");

        Arc arc3 = new Arc();
        arc3.setWeight(1);
        arc3.setSourceId("2");
        arc3.setDestinationId("4");

        Arc arc4 = new Arc();
        arc4.setWeight(1);
        arc4.setSourceId("3");
        arc4.setDestinationId("5");

        PetriNet petriNet = new PetriNet();
        petriNet.setTransitions(Arrays.asList(transition, transition2));
        petriNet.setPlaces(Arrays.asList(concurrentPlace, place2, place3));
        petriNet.setArcs(Arrays.asList(arc, arc2, arc3, arc4));

        petriNet.run();

        assertTrue((place2.getTokens() == 1 || place3.getTokens() == 1) && (concurrentPlace.getTokens() == 0));

    }

    @Test
    public void testConflictScenarioWithThreeTransitions() {
        Place concurrentPlace = new Place();
        concurrentPlace.setId("1");
        concurrentPlace.setTokens(1);

        Transition transition = new Transition();
        transition.setId("2");

        Transition transition2 = new Transition();
        transition2.setId("3");

        Transition transition3 = new Transition();
        transition3.setId("13");

        Place place2 = new Place();
        place2.setId("4");

        Place place3 = new Place();
        place3.setId("5");

        Place place4 = new Place();
        place4.setId("15");

        Arc arc = new Arc();
        arc.setWeight(1);
        arc.setSourceId("1");
        arc.setDestinationId("2");

        Arc arc2 = new Arc();
        arc2.setWeight(1);
        arc2.setSourceId("1");
        arc2.setDestinationId("3");

        Arc arc5 = new Arc();
        arc5.setWeight(1);
        arc5.setSourceId("1");
        arc5.setDestinationId("13");

        Arc arc6 = new Arc();
        arc6.setWeight(1);
        arc6.setSourceId("13");
        arc6.setDestinationId("15");

        Arc arc3 = new Arc();
        arc3.setWeight(1);
        arc3.setSourceId("2");
        arc3.setDestinationId("4");

        Arc arc4 = new Arc();
        arc4.setWeight(1);
        arc4.setSourceId("3");
        arc4.setDestinationId("5");

        PetriNet petriNet = new PetriNet();
        petriNet.setTransitions(Arrays.asList(transition, transition2, transition3));
        petriNet.setPlaces(Arrays.asList(concurrentPlace, place2, place3, place4));
        petriNet.setArcs(Arrays.asList(arc, arc2, arc3, arc4, arc5, arc6));

        petriNet.run();

        assertTrue((place2.getTokens() == 1 || place3.getTokens() == 1 || place4.getTokens() == 1) && (concurrentPlace.getTokens() == 0));

    }

}
