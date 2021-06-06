import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.unisinos.petri.Arc;
import com.unisinos.petri.Document;
import com.unisinos.petri.PetriNet;
import com.unisinos.petri.Place;
import com.unisinos.petri.Transition;

import org.junit.Before;
import org.junit.Test;


public class UnmarshallerTest {

    @Before
    public void before() {
        File file = new File("src/test/java/simple_net.pflow");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            this.document = (Document) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    Document document; 

    @Test
    public void testSimpleNet()
    {
        PetriNet subnet = document.getSubnet().get(0);
        assertNotNull(subnet);
        assertEquals(subnet.getPlaces().size(), 2);
        assertEquals(subnet.getTransitions().size(), 1);
        List<Place> places = subnet.getPlaces();
        Place firstPlace = places.get(0);
        assertEquals(firstPlace.getId(), "37");
        assertEquals(firstPlace.getTokens(), 1);
        Place secondPlace = places.get(1);
        assertEquals(secondPlace.getId(), "38");
        assertEquals(secondPlace.getTokens(), 0);
        List<Transition> transitions = subnet.getTransitions();
        Transition transition = transitions.get(0);
        assertEquals(transition.getId(), "39");
        List<Arc> arcs = subnet.getArcs();
        Arc firstArc = arcs.get(0);
        assertEquals(firstArc.getSourceId(), "39");
        assertEquals(firstArc.getDestinationId(), "38");
        Arc secondArc = arcs.get(1);
        assertEquals(secondArc.getSourceId(), "37");
        assertEquals(secondArc.getDestinationId(), "39");
    }

    @Test
    public void testGetInputAndOutputArcs() {
        PetriNet subnet = document.getSubnet().get(0);

        List<Arc> inputArcs = subnet.getTransitions().get(0).getInputArcs(subnet);
        Arc inputArc = inputArcs.get(0);
        assertEquals(inputArcs.size(), 1);
        assertEquals(inputArc.getSourceId(), "37");
        assertEquals(inputArc.getDestinationId(), "39");

        List<Arc> outputArcs = subnet.getTransitions().get(0).getOutputArcs(subnet);
        Arc outputArc = outputArcs.get(0);
        assertEquals(outputArcs.size(), 1);
        assertEquals(outputArc.getSourceId(), "39");
        assertEquals(outputArc.getDestinationId(), "38");
    }

    @Test
    public void arcGetPlaceAndGetTransition() {
        PetriNet subnet = document.getSubnet().get(0);

        List<Arc> inputArcs = subnet.getTransitions().get(0).getInputArcs(subnet);
        Arc inputArc = inputArcs.get(0);

        assertEquals(inputArc.getPlace(subnet).getId(), "37");
        assertEquals(inputArc.getTransition(subnet).getId(), "39");

        List<Arc> outputArcs = subnet.getTransitions().get(0).getOutputArcs(subnet);
        Arc outputArc = outputArcs.get(0);

        assertEquals(outputArc.getPlace(subnet).getId(), "38");
        assertEquals(outputArc.getTransition(subnet).getId(), "39");
    }

}