package com.unisinos.petri;
import javax.xml.bind.annotation.XmlElement;

public class Place {

    private String id;
    private String label;
    private int tokens;
    private int tokensToBeAdded = 0;

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

    public void setTokensAfterCycle(){
        tokens += tokensToBeAdded;
        tokensToBeAdded = 0;
    }

    public int getTokensToBeAdded() {
        return tokensToBeAdded;
    }

    public void setTokensToBeAdded(int tokensToBeAdded) {
        this.tokensToBeAdded += tokensToBeAdded;
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
        return String.format("%5s", label);
    }
}
