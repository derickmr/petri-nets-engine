import java.util.Stack;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Place {

    private String id;
    private String label;
    private Stack<Token> tokens;
    private int tokens2;

    public String getId() {
        return id;
    }

    @XmlElement(name="tokens")
    public int getTokens2() {
        return tokens2;
    }

    public void setTokens2(int tokens2) {
        this.tokens2 = tokens2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Stack<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Stack<Token> tokens) {
        this.tokens = tokens;
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
