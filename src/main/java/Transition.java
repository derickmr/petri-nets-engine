import java.util.List;

public class Transition {

    private String id;
    private List<Arc> inputArcs;
    private List<Arc> outputArcs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isEnabled(){
        //TODO
        return true;
    }

    public String isEnabledString(){
        return isEnabled() ? "S" : "N";
    }

    @Override
    public String toString() {
        return id;
    }
}
