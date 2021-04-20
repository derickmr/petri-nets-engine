import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="transition")
public class Transition {

    private String id;
    private String label;
    private List<Arc> inputArcs;
    private List<Arc> outputArcs;
    
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

    public List<Arc> getInputArcs() {
        return inputArcs;
    }

    public void setInputArcs(List<Arc> inputArcs) {
        this.inputArcs = inputArcs;
    }

    public List<Arc> getOutputArcs() {
        return outputArcs;
    }

    public void setOutputArcs(List<Arc> outputArcs) {
        this.outputArcs = outputArcs;
    }

    public void fire(){
        while (isEnabled()) {
            inputArcs.forEach(Arc::fireInputArc);
            outputArcs.forEach(Arc::fireOutputArc);
        }
    }

    public boolean isEnabled(){
        return inputArcs.stream().allMatch(Arc::canFire);
    }

    public String isEnabledString(){
        return isEnabled() ? "S" : "N";
    }

    @Override
    public String toString() {
        return id;
    }

    public void fireOnlyOnce() {
        inputArcs.forEach(Arc::fireInputArc);
        outputArcs.forEach(Arc::fireOutputArc);
    }
}
