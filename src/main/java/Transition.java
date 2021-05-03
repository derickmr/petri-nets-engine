import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name="transition")
public class Transition {

    private String id;
    private String label;
    
    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlElement
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Arc> getInputArcs(PetriNet petriNet) {
        List<Arc> inputArcs = petriNet.getArcs().stream().filter(arc -> id.equalsIgnoreCase(arc.getDestinationId())).collect(Collectors.toList());
        return inputArcs;
    }

    public List<Arc> getOutputArcs(PetriNet petriNet) {
        List<Arc> outputArcs = petriNet.getArcs().stream().filter(arc -> id.equalsIgnoreCase(arc.getSourceId())).collect(Collectors.toList());
        return outputArcs;
    }

    public void fire(PetriNet petriNet){
        if (containsOnlySpecialArcs(petriNet)){
            fireOnlyOnce(petriNet);
        } else {
            while (isEnabled(petriNet)) {
                getInputArcs(petriNet).forEach(arc -> arc.fireInputArc(petriNet));
                getOutputArcs(petriNet).forEach(arc -> arc.fireOutputArc(petriNet));
            }
        }
    }

    private boolean containsOnlySpecialArcs(PetriNet petriNet) {
        return getInputArcs(petriNet).stream().allMatch(this::isSpecialArc);
    }

    private boolean isSpecialArc(Arc arc) {
        return arc instanceof InhibitorArc || arc instanceof ResetArc;
    }

    public boolean isEnabled(PetriNet petriNet){
        return getInputArcs(petriNet).stream().allMatch(arc -> arc.canFire(petriNet));
    }

    public String isEnabledString(PetriNet petriNet){
        String result = isEnabled(petriNet) ? "S" : "N";
        return String.format("%5s", result);
    }

    @Override
    public String toString() {
        return String.format("%5s", label);
    }

    public void fireOnlyOnce(PetriNet petriNet) {
        getInputArcs(petriNet).forEach(arc -> arc.fireInputArc(petriNet));
        getOutputArcs(petriNet).forEach(arc -> arc.fireOutputArc(petriNet));
    }
}
