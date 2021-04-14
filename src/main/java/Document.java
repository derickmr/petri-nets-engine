import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="document")
public class Document {
    @XmlElement(name="subnet")
    private List<PetriNet> petriNet;
    public List<PetriNet> getSubnet() {
        return petriNet;
    }
    public void setSubnet(List<PetriNet> net) {
        this.petriNet = net;
    }
}
