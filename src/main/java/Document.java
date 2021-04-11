import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name="document")
public class Document {
    private List<PetriNet> petriNet;

    @XmlElement(name="subnet")
    public List<PetriNet> getNets() {
        return petriNet;
    }

}
