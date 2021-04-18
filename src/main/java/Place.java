import javax.xml.bind.annotation.XmlElement;

public class Place {

    private String id;
    private String label;
    private int tokens;

    public String getId() {
        return id;
    }

    @XmlElement(name="tokens")
    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return id;
    }
}
