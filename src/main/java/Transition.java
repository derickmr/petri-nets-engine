import java.util.List;

public class Transition {

    private List<Arc> inputArcs;
    private List<Arc> outputArcs;

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

    public boolean isEnabled(){
        //TODO
        return true;
    }

    public String isEnabledString(){
        return isEnabled() ? "S" : "N";
    }
}
